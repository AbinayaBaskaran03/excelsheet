package com.excelsheet.controller;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excelsheet.entity.Details;
import com.excelsheet.repository.DetailsRepository;
import com.excelsheet.service.DetailsService;

@RestController
@RequestMapping("/excel")
public class DetailsController {

	@Autowired
	private DetailsService detailsService;

	@Autowired
	private DetailsRepository detailsRepository;

	@PostMapping(value = "/createdetails")
	public ResponseEntity<String> createDetails(@RequestBody Details details) {
		detailsService.saveDetails(details);
		return ResponseEntity.ok("Details uploaded");
	}

//Download Excel
	@GetMapping(value = "/getdetails")
	public ResponseEntity<Resource> downloadDetails() {
		List<Details> details = detailsService.getAlldetails();
		ByteArrayResource resource = ExcelUtil.generateExcel(details);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=Detailsheet.xlsx")
				.contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(resource.contentLength()).body(resource);
	}

//DownloadExcel ByName	 
	@GetMapping(value = "/getdetailsbyname/{name}")
	public ResponseEntity<Resource> getbyname(@PathVariable("name") String name) {
		List<Details> detailsObj = detailsService.findByname(name);
		ByteArrayResource resource = ExcelUtil.generateExcel(detailsObj);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=ByNamesheet.xlsx")
				.contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(resource.contentLength()).body(resource);
	}

//Download PDF
	@GetMapping(value = "/downloadPdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> downloadPDF() {
		List<Details> pdfObj = (List<Details>) detailsRepository.findAll();
		ByteArrayInputStream resource = detailsService.generatePDF(pdfObj);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=DetailsPDF.pdf")
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(new InputStreamResource(resource));
	}

}

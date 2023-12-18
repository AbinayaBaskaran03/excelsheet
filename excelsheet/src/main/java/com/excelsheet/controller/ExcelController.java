package com.excelsheet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.excelsheet.entity.ExcelEntity;
import com.excelsheet.service.ExcelService;

@RestController
@RequestMapping("/excel")
public class ExcelController {

	@Autowired
	private ExcelService excelService;

	@PostMapping("/uploadExcel")
	public ResponseEntity<String> uploadExcelFile(@RequestParam("file") MultipartFile file) {
		List<ExcelEntity> data = excelService.saveDataFromExcel(file);

		return ResponseEntity.ok("File uploaded successfully.");
	}
}

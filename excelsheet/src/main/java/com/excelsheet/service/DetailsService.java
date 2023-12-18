package com.excelsheet.service;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excelsheet.entity.Details;
import com.excelsheet.repository.DetailsRepository;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class DetailsService {

	@Autowired
	public DetailsRepository detailsRepository;

	public void saveDetails(Details details) {
		detailsRepository.save(details);
	}

	public List<Details> getAlldetails() {
		return detailsRepository.findAll();
	}

	public List<Details> findByname(String name) {
		return detailsRepository.findByName(name);
	}

	public static ByteArrayInputStream generatePDF(List<Details> pdfObj) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			PdfWriter.getInstance(document, out);
			document.open();

			Font fontHeader = FontFactory.getFont(FontFactory.TIMES_BOLD, 22);
			fontHeader.setColor(Color.BLACK);
			Paragraph para = new Paragraph("USER DETAILS", fontHeader);
			para.setAlignment(Element.ALIGN_CENTER);
			document.add(para);
			document.add(Chunk.NEWLINE);

			PdfPTable tableObj = new PdfPTable(7);

			Stream.of("S.NO", "NAME", "ADDRESS", "DOB", "EMAIL", "PHONE", "STATUS").forEach(headerTitle -> {
				PdfPCell header = new PdfPCell();
				Font headFont = FontFactory.getFont(FontFactory.TIMES_ROMAN);
				header.setBackgroundColor(Color.cyan);
				header.setHorizontalAlignment(Element.ALIGN_CENTER);
				header.setBorderWidth(1);
				header.setPhrase(new Phrase(headerTitle, headFont));
				tableObj.addCell(header);
			});

			int sNo = 1;
			for (Details detObj : pdfObj) {
				PdfPCell sNoCell = new PdfPCell(new Phrase(String.valueOf(sNo)));
				sNoCell.setPaddingLeft(4);
				sNoCell.setBorderWidth(1);
				sNoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				sNoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tableObj.addCell(sNoCell);
				sNo++;

				PdfPCell nameObj = new PdfPCell(new Phrase(detObj.getName()));
				nameObj.setPaddingLeft(4);
				nameObj.setBorderWidth(1);
				nameObj.setVerticalAlignment(Element.ALIGN_MIDDLE);
				nameObj.setHorizontalAlignment(Element.ALIGN_CENTER);
				tableObj.addCell(nameObj);

				PdfPCell addObj = new PdfPCell(new Phrase(detObj.getAddress()));
				addObj.setPaddingLeft(4);
				addObj.setBorderWidth(1);
				addObj.setVerticalAlignment(Element.ALIGN_MIDDLE);
				addObj.setHorizontalAlignment(Element.ALIGN_CENTER);
				tableObj.addCell(addObj);

				PdfPCell dobObj = new PdfPCell(new Phrase(sdf.format(detObj.getDob())));
				dobObj.setPaddingLeft(4);
				dobObj.setBorderWidth(1);
				dobObj.setVerticalAlignment(Element.ALIGN_MIDDLE);
				dobObj.setHorizontalAlignment(Element.ALIGN_CENTER);
				tableObj.addCell(dobObj);

				PdfPCell emailObj = new PdfPCell(new Phrase(detObj.getEmail()));
				emailObj.setPaddingLeft(4);
				emailObj.setBorderWidth(1);
				emailObj.setVerticalAlignment(Element.ALIGN_MIDDLE);
				emailObj.setHorizontalAlignment(Element.ALIGN_CENTER);
				tableObj.addCell(emailObj);

				PdfPCell phoneObj = new PdfPCell(new Phrase(detObj.getPhone()));
				phoneObj.setPaddingLeft(4);
				phoneObj.setBorderWidth(1);
				phoneObj.setVerticalAlignment(Element.ALIGN_MIDDLE);
				phoneObj.setHorizontalAlignment(Element.ALIGN_CENTER);
				tableObj.addCell(phoneObj);

				PdfPCell stsObj = new PdfPCell(new Phrase(detObj.getStatus()));
				stsObj.setPaddingLeft(4);
				stsObj.setBorderWidth(1);
				stsObj.setVerticalAlignment(Element.ALIGN_MIDDLE);
				stsObj.setHorizontalAlignment(Element.ALIGN_CENTER);
				tableObj.addCell(stsObj);
			}

			document.add(tableObj);

			document.close();
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return new ByteArrayInputStream(out.toByteArray());
	}
}

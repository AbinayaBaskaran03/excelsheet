package com.excelsheet.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;

import com.excelsheet.entity.Details;

public class ExcelUtil {
	
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(date);
    }


	public static ByteArrayResource generateExcel(List<Details> details) {
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Data");
			
			 if (details != null) {
	                Collections.sort(details, Comparator.comparing(Details::getName));
	            }

			Row headerRow = sheet.createRow(0);
			headerRow.createCell(0).setCellValue("S.No");
			headerRow.createCell(1).setCellValue("name");
			headerRow.createCell(2).setCellValue("address");
			headerRow.createCell(3).setCellValue("dob");
			headerRow.createCell(4).setCellValue("email");
			headerRow.createCell(5).setCellValue("phone");
			headerRow.createCell(6).setCellValue("status");

			if (details != null) {

				for (int i = 0; i < details.size(); i++) {
					Row row = sheet.createRow(i + 1);
					row.createCell(0).setCellValue(i + 1); 
					Details detailsObj = details.get(i);

					row.createCell(1).setCellValue(detailsObj.getName());
					row.createCell(2).setCellValue(detailsObj.getAddress());
                    row.createCell(3).setCellValue(formatDate(detailsObj.getDob()));
					row.createCell(4).setCellValue(detailsObj.getEmail());
					row.createCell(5).setCellValue(detailsObj.getPhone());
					row.createCell(6).setCellValue(detailsObj.getStatus());
					

				}

			}

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);

			return new ByteArrayResource(outputStream.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	
}

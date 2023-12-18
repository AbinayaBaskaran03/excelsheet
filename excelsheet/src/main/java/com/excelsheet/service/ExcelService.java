package com.excelsheet.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.excelsheet.entity.ExcelEntity;
import com.excelsheet.repository.ExcelRep;

@Service
public class ExcelService {

	@Autowired
	private ExcelRep excelRep;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public List<ExcelEntity> saveDataFromExcel(MultipartFile file) {
		try (InputStream inputStream = file.getInputStream()) {
			List<ExcelEntity> data = new ArrayList<>();

			Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook(inputStream);
			Sheet sheet = workbook.getSheetAt(0);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				ExcelEntity entity = new ExcelEntity();
				entity.setId(UUID.randomUUID());
				entity.setName(getStringCellValue(row.getCell(0)));
				entity.setAddress(getStringCellValue(row.getCell(1)));
				entity.setDob(getDateCellValue(row.getCell(2)));
				entity.setEmail(getStringCellValue(row.getCell(3)));
				entity.setPhone(getStringCellValue(row.getCell(4)));
				entity.setStatus(getStringCellValue(row.getCell(5)));

				data.add(entity);
			}

			return excelRep.saveAll(data);

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private String getStringCellValue(Cell cell) {
		if (cell == null) {
			return null;
		}
		if (cell.getCellType() == CellType.STRING) {
			return cell.getStringCellValue();
		} else {
			return String.valueOf(cell.getNumericCellValue());
		}
	}

	private Date getDateCellValue(Cell cell) {
		if (cell == null) {
			return null;
		}
		if (cell.getCellType() == CellType.NUMERIC) {
			return cell.getDateCellValue();
		} else if (cell.getCellType() == CellType.STRING) {
			try {
				return sdf.parse(cell.getStringCellValue());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}

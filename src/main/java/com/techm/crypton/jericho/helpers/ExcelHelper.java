package com.techm.crypton.jericho.helpers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.techm.crypton.jericho.models.Records;

public class ExcelHelper {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "year", "period", "entity", "numberOfItems","createdBy","createdOn" };
	static String SHEET = "Records";
	
	public static boolean hasExcelFormat(MultipartFile file) {
		if(!TYPE.equals(file.getContentType()))
			return false;
		return true;
	}
	
	public static List<Records> excelToRecords(InputStream in) {
		try {
			Workbook workbook = new XSSFWorkbook(in);
			
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rows = sheet.iterator();
			
			List<Records> records = new ArrayList<Records>();
			
			int rowNum = 0;
			while(rows.hasNext()) {
				Row currentRow = rows.next();
				
				if(rowNum == 0) {
					rowNum++;
					continue;
				}
				
				Iterator<Cell> cellsInRow = currentRow.iterator();
				Records record = new Records();
				
				int cellIdx = 0;
				while(cellsInRow.hasNext()) {
					Cell currCell = cellsInRow.next();
					switch(cellIdx) {
					case 0:
						record.setYear((short) currCell.getNumericCellValue());
						break;
					case 1:
						record.setPeriod(currCell.getStringCellValue());
						break;
					case 2:
						record.setEntity(currCell.getStringCellValue());
						break;
					case 3:
						record.setNumberOfItems((int)currCell.getNumericCellValue());
						break;
					default:
						break;
					}
					cellIdx++;
				}
				records.add(record);
			}
			workbook.close();
			return records;
		} catch(IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
	}
	
	public static ByteArrayInputStream recordsToExcel(List<Records> records) {
		
		try(
				Workbook workbook = new XSSFWorkbook();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				) {
			Sheet sheet = workbook.createSheet(SHEET);
			
			Row headerRow = sheet.createRow(0);
			
			for(int col = 0; col < HEADERs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(HEADERs[col]);
			}
			DateFormat d = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
			
			int rowIdx = 1;
			for(Records record : records) {
				Row row = sheet.createRow(rowIdx++);
				
				row.createCell(0).setCellValue(record.getYear());
				row.createCell(1).setCellValue(record.getPeriod());
				row.createCell(2).setCellValue(record.getEntity());
				row.createCell(3).setCellValue(record.getNumberOfItems());
				row.createCell(4).setCellValue(record.getCreatedBy());
				row.createCell(5).setCellValue(d.format(record.getCreatedOn()));
			}
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
		}
	}
}

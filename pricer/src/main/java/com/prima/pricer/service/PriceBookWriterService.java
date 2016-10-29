package com.prima.pricer.service;

import com.prima.pricer.interfaces.PriceBookWriterFacade;
import com.prima.pricer.model.PriceBook;
import com.prima.pricer.model.PriceBookRecord;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.util.List;

public class PriceBookWriterService extends AbstractService implements PriceBookWriterFacade {

	@Override
	public boolean writeResultBook(PriceBook resultBook) {

		try {
			String path;
			path = resultBook.getObjectToProcessing().getPathToExcel().replaceAll("prices\\\\p[0-9]{1,2}", "result");
			System.out.println("result path = " + path);

			Workbook workbook = new XSSFWorkbook();
			CreationHelper createHelper = workbook.getCreationHelper();
			Sheet sheet = workbook.createSheet("prices");

			CellStyle style = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setFontName("Times New Roman");
			font.setFontHeightInPoints((short) 12);
			style.setFont(font);

			List<PriceBookRecord> list = (List<PriceBookRecord>) resultBook.getRecords();
			for (int i = 0; i < list.size(); i++) {
				Row row = sheet.createRow(i);
				PriceBookRecord record = list.get(i);
				System.out.println("Save record: " + record);

				row.createCell(0).setCellValue(createHelper.createRichTextString(record.getArticul()));
				row.createCell(1).setCellValue(createHelper.createRichTextString(record.getName()));
				row.createCell(2).setCellValue(createHelper.createRichTextString(record.getPrice()));
				row.createCell(3).setCellValue(createHelper.createRichTextString(record.getQuantity()));
				row.createCell(4).setCellValue(record.hasRetailPrice());
				if (record.hasRetailPrice()) {
					row.createCell(5).setCellValue(record.getRetailPriceMultiplierPercent());
				}
				row.createCell(6).setCellValue(record.isAvailable());

				for (int j = 0; j < 7; j++) {
					try {
						row.getCell(j).setCellStyle(style);
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
				}
			}

			FileOutputStream outputStream = new FileOutputStream(path);
			workbook.write(outputStream);
			outputStream.close();

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}
}
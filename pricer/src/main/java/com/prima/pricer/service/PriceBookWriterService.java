package com.prima.pricer.service;

import com.prima.pricer.interfaces.PriceBookWriterFacade;
import com.prima.pricer.model.PriceBook;
import com.prima.pricer.model.PriceBookRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Iterator;

public class PriceBookWriterService extends AbstractService implements PriceBookWriterFacade {

    protected void createHeader(Sheet sheet, PriceBookRecord firstRecord, CreationHelper createHelper) {
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue(createHelper.createRichTextString(firstRecord.getArticul()));
        row.createCell(1).setCellValue(createHelper.createRichTextString(firstRecord.getName()));
        row.createCell(2).setCellValue(createHelper.createRichTextString(firstRecord.getPrice()));
        row.createCell(3).setCellValue(createHelper.createRichTextString(firstRecord.getQuantity()));
        row.createCell(4).setCellValue("ЕСТЬ_РОЗНИЧНАЯ_ЦЕНА");
        row.createCell(5).setCellValue("ПРОЦЕНТ_РОЗНИЧНОЙ_ЦЕНЫ");
        row.createCell(6).setCellValue("ДОСТУПНО");
        row.createCell(7).setCellValue("НОВЫЙ");
    }

    @Override
    public boolean writeResultBook(PriceBook resultBook) {

        try {
            String path;
            path = composePath(
                    resultBook.getObjectToProcessing().getPathToExcel());

            System.out.println("result path = " + path);

            Workbook workbook = new XSSFWorkbook();
            CreationHelper createHelper = workbook.getCreationHelper();
            Sheet sheet = workbook.createSheet("prices");

            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setFontName("Times New Roman");
            font.setFontHeightInPoints((short) 12);
            style.setFont(font);

            Collection<PriceBookRecord> records = resultBook.getRecords();
            Iterator<PriceBookRecord> iter = records.iterator();
            int i = 0;
            while (iter.hasNext()) {
                PriceBookRecord record = iter.next();
                if (StringUtils.isEmpty(record.getArticul())
                        || StringUtils.isEmpty(record.getPrice())
                        || StringUtils.isEmpty(record.getQuantity())) {
                    continue;

                }
                if (i == 0) {
                    createHeader(sheet, record, createHelper);
                    i++;
                    continue;
                }
                Row row = sheet.createRow(i++);
                System.out.println("Save record: " + record);

                // TODO : inject separate method for raw creation and write down.
                row.createCell(0).setCellValue(createHelper.createRichTextString(record.getArticul()));
                row.createCell(1).setCellValue(createHelper.createRichTextString(record.getName()));
                row.createCell(2).setCellValue(createHelper.createRichTextString(record.getPrice()));
                row.createCell(3).setCellValue(createHelper.createRichTextString(record.getQuantity()));
                row.createCell(4).setCellValue(record.hasRetailPrice());
                if (record.hasRetailPrice()) {
                    row.createCell(5).setCellValue(record.getRetailPriceMultiplierPercent());
                } else {
                    row.createCell(5);
                }
                boolean isAvailable = availabilityDeterminerSvc
                        .determineIsAvailable(record.getQuantity(),
                                resultBook.getObjectToProcessing().getRoot().isAvailabilityOnExistence());
                row.createCell(6).setCellValue(isAvailable);

                row.createCell(7).setCellValue(record.isNew());

                for (int j = 0; j < 8; j++) {
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

    @Override
    public boolean writeArchivedResultBook(PriceBook resultBook) {
        try {
            String source = composePath(resultBook.getObjectToProcessing().getPathToExcel());
            String destination = appendArchiveToFileName(appendTimeStampToFileName(source));

            logger.debug("String source = " + source);
            logger.debug("String source = " + destination);

            Files.copy(Paths.get(source), Paths.get(destination));
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

}
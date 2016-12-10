package com.prima.pricer.service;

import com.prima.pricer.interfaces.PriceBookWriterFacade;
import com.prima.pricer.interfaces.SiteIdReaderFacade;
import com.prima.pricer.model.PriceBook;
import com.prima.pricer.model.PriceBookRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class PriceBookWriterService extends AbstractService implements PriceBookWriterFacade {

    private SiteIdReaderFacade siteIdReaderFacade;

    protected void createHeader(Sheet sheet, CellStyle style) {
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("АРТИКУЛ");
        row.createCell(1).setCellValue("НАИМЕНОВАНИЕ");
        row.createCell(2).setCellValue("ЦЕНА");
        row.createCell(3).setCellValue("КОЛИЧЕСТВО");
        row.createCell(4).setCellValue("ЕСТЬ_РОЗНИЧНАЯ_ЦЕНА");
        row.createCell(5).setCellValue("ПРОЦЕНТ_РОЗНИЧНОЙ_ЦЕНЫ");
        row.createCell(6).setCellValue("ДОСТУПНО");
        row.createCell(7).setCellValue("НОВЫЙ");
        row.createCell(8).setCellValue("ID_С_САЙТА");
        row.createCell(9).setCellValue("ДОСТУПНО_+");
        setRowStyle(style, row);
    }

    @Override
    public boolean writeResultBook(PriceBook resultBook) {

        try {
            String path;
            path = composePath(
                    resultBook.getObjectToProcessing().getPathToExcel());

            logger.info("Write result: Path = " + path);

            Workbook workbook = new XSSFWorkbook();
            CreationHelper createHelper = workbook.getCreationHelper();
            Sheet sheet = workbook.createSheet("prices");

            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setFontName("Times New Roman");
            font.setFontHeightInPoints((short) 12);
            style.setFont(font);

            Collection<PriceBookRecord> records = resultBook.getRecords();
            Iterator<PriceBookRecord> iterator = records.iterator();
            int i = 0;
            Set<String> priceAvailableArticuls = new HashSet<>();
            while (iterator.hasNext()) {
                PriceBookRecord record = iterator.next();
                if (StringUtils.isEmpty(record.getArticul())
                        || StringUtils.isEmpty(record.getPrice())
                        || StringUtils.isEmpty(record.getQuantity())) {
                    continue;

                }
                if (i == 0) {
                    createHeader(sheet, style);
                    i++;
                    continue;
                }
                priceAvailableArticuls.add(record.getArticul());
                i = setRowValues(resultBook, createHelper, sheet, style, i, record);
            }

            Set<String> existingArticulsInPropsFile = siteIdReaderFacade.getExistingArticulsInPropsFile(resultBook.getObjectToProcessing().getRoot().getSupplierId());
            logger.debug("Total count id's from site = " + existingArticulsInPropsFile.size());
            existingArticulsInPropsFile.removeAll(priceAvailableArticuls);
            logger.debug("Count id's from site, that hasn't analogs in p*.xlsx file = " + existingArticulsInPropsFile.size());

            for (String articul : existingArticulsInPropsFile) {
                PriceBookRecord record = new PriceBookRecord();
                record.setSupplierId(resultBook.getObjectToProcessing().getRoot().getSupplierId());
                record.setArticul(articul);
                record.setName(siteIdReaderFacade.getSiteIdSiteName(record.getSupplierId(), articul).getRight());
                record.setPrice("0");
                record.setQuantity("0");
                record.setRetailPrice(false);
                record.setRetailPriceMultiplierPercent(0D);
                record.setAvailable(false);
                record.setNew(false);
                record.setRowNumber(0);
                i = setRowValues(resultBook, createHelper, sheet, style, i, record);
            }

            FileOutputStream outputStream = new FileOutputStream(path);
            workbook.write(outputStream);
            outputStream.close();

            return true;
        } catch (Exception ex) {
            logger.error("", ex);
            return false;
        }
    }

    protected int setRowValues(PriceBook resultBook, CreationHelper createHelper, Sheet sheet, CellStyle style, int i, PriceBookRecord record) {
        Row row = sheet.createRow(i++);
        logger.debug("Save record: " + record);

        row.createCell(0).setCellValue(createHelper.createRichTextString(record.getArticul()));

        row.createCell(2).setCellValue(createHelper.createRichTextString(record.getPrice()));
        row.createCell(3).setCellValue(createHelper.createRichTextString(record.getQuantity()));
        row.createCell(4).setCellValue(record.hasRetailPrice());
        if (record.getRetailPriceMultiplierPercent() != null) {
            row.createCell(5).setCellValue(record.getRetailPriceMultiplierPercent());
        } else {
            row.createCell(5);
        }
        boolean isAvailable = false;
        if (record.isAvailable()) {
            isAvailable = availabilityDeterminerSvc
                    .determineIsAvailable(record.getQuantity(),
                            resultBook.getObjectToProcessing().getRoot().isAvailabilityOnExistence());
        }
        row.createCell(6).setCellValue(isAvailable);
        row.createCell(7).setCellValue(record.isNew());

        String siteId = null;
        String siteName = null;

        Pair<String, String> siteIdSiteName = siteIdReaderFacade.getSiteIdSiteName(record.getSupplierId(), record.getArticul());
        if (siteIdSiteName != null) {
            siteId = siteIdSiteName.getLeft();
            siteName = siteIdSiteName.getRight();
        }
        if (siteId != null) {
            row.createCell(8).setCellValue(siteId);
        } else {
            row.createCell(8).setCellValue("");
        }
        if (siteName != null && !siteName.equals("")) {
            row.createCell(1).setCellValue(siteName);
        } else {
            row.createCell(1).setCellValue(createHelper.createRichTextString(record.getName()));
        }

        if (isAvailable) {
        	row.createCell(9).setCellValue("+");
        } else {
            row.createCell(9).setCellValue("-");
        }

        setRowStyle(style, row);
        return i;
    }

    protected void setRowStyle(CellStyle style, Row row) {
        for (int j = 0; j < 10; j++) {
            try {
                row.getCell(j).setCellStyle(style);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }

    @Override
    public boolean writeArchivedResultBook(PriceBook resultBook) {
        try {
            String source = composePath(resultBook.getObjectToProcessing().getPathToExcel());
            String destination = appendArchiveToFileName(appendTimeStampToFileName(source));

            logger.info("Archiving:\tString source = " + source);
            logger.info("Archiving:\tString destination = " + destination);

            Files.copy(Paths.get(source), Paths.get(destination));
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public void setSiteIdReaderFacade(SiteIdReaderFacade siteIdReaderFacade) {
        this.siteIdReaderFacade = siteIdReaderFacade;
    }
}
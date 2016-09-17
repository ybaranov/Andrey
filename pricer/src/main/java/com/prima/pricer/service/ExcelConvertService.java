package com.prima.pricer.service;

import com.prima.pricer.interfaces.ExcelConvertFacade;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class ExcelConvertService extends AbstractService implements ExcelConvertFacade {
    public static final Path path = Paths.get("C:\\andrey\\prices\\");
    private int numColumn = 0;

    @Override
    public void prepareAllBooks() {
        try {
            List<Path> paths = Files.walk(path)
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
            for (Path file : paths) {
                if (file.toString().toLowerCase().endsWith(".xls")) {
                    System.out.println("Begin working on file " + file.toFile().getName());
                    Workbook workBookPOI;

                    FileInputStream inputStream;
                    Workbook workXBookPOI = new XSSFWorkbook();
                    inputStream = new FileInputStream(file.toFile());
                    workBookPOI = new HSSFWorkbook(inputStream);

                    String sheetName = workBookPOI.getSheetAt(0).getSheetName();
                    convertSheet(workBookPOI.getSheetAt(0), workXBookPOI.createSheet(sheetName));

                    FileOutputStream outputStream = new FileOutputStream(file.toFile() + "x");
                    workXBookPOI.write(outputStream);
                    outputStream.close();
                    workBookPOI.close();
                    workXBookPOI.close();
                    System.out.println("End working on file " + file.toFile().getName());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод конвертирующий Лист формата xls в лист формата xlsx
     *
     * @param sheetOld - заполненный лист формата xls
     * @param sheetNew - лист в который происходит запись формата xlsx
     */
    public void convertSheet(Sheet sheetOld, Sheet sheetNew) {

        //Задаём передачу параметров со старого документа в новый
        sheetNew.setDisplayFormulas(sheetOld.isDisplayFormulas());
        sheetNew.setDisplayGridlines(sheetOld.isDisplayGridlines());
        sheetNew.setDisplayGuts(sheetOld.getDisplayGuts());
        sheetNew.setDisplayRowColHeadings(sheetOld.isDisplayRowColHeadings());
        sheetNew.setDisplayZeros(sheetOld.isDisplayZeros());
        sheetNew.setFitToPage(sheetOld.getFitToPage());
        sheetNew.setForceFormulaRecalculation(sheetOld.getForceFormulaRecalculation());
        sheetNew.setHorizontallyCenter(sheetOld.getHorizontallyCenter());
        sheetNew.setPrintGridlines(sheetNew.isPrintGridlines());
        sheetNew.setRightToLeft(sheetNew.isRightToLeft());
        sheetNew.setRowSumsBelow(sheetNew.getRowSumsBelow());
        sheetNew.setRowSumsRight(sheetNew.getRowSumsRight());
        sheetNew.setVerticallyCenter(sheetOld.getVerticallyCenter());

        //Работаем с строками
        XSSFRow rowNew;

        for (Row row : sheetOld) {
            rowNew = (XSSFRow) sheetNew.createRow(row.getRowNum());
            if (rowNew != null) {
                convertRow((HSSFRow) row, rowNew);
            }
        }
        //задаём  ширину и высоту для колонок
        for (int i = 0; i < this.numColumn; i++) {
            sheetNew.setColumnWidth(i, sheetOld.getColumnWidth(i));
            sheetNew.setColumnHidden(i, sheetOld.isColumnHidden(i));
        }
        //задаём объединение ячеек.
        for (int i = 0; i < sheetOld.getNumMergedRegions(); i++) {
            CellRangeAddress merged = sheetOld.getMergedRegion(i);
            sheetNew.addMergedRegion(merged);
        }
    }

    /**
     * Преобразование строк в apache POI
     *
     * @param rowOld  - строка из листа xls
     * @param rowNew  - строка из листа xlsx
     */
    private void convertRow(HSSFRow rowOld, XSSFRow rowNew) {
        XSSFCell cellNew;
        rowNew.setHeight(rowOld.getHeight());
        for (Cell cell : rowOld) {
            //Создаём новые ячейки в xlsx-документе по числу и расположению в xls-ком
            cellNew = rowNew.createCell(cell.getColumnIndex(), cell.getCellType());
            if (cellNew != null) {
                this.convertCell((HSSFCell) cell, cellNew);
            }
        }
        this.numColumn = Math.max(this.numColumn, rowOld.getLastCellNum());
    }

    /**
     * Конвертирование ячеек в apache POI
     *
     * @param cellOld - ячейка формата xls
     * @param cellNew - ячейка формата xlsx
     */
    private void convertCell(HSSFCell cellOld, XSSFCell cellNew) {
        cellNew.setCellComment(cellOld.getCellComment());
        Hyperlink linkOld = cellOld.getHyperlink();

        try {
            if(linkOld != null){
                CellStyle hyperlinkStyle = cellNew.getSheet().getWorkbook().createCellStyle();
                Font hyperlinkFont = cellNew.getSheet().getWorkbook().createFont();
                hyperlinkFont.setUnderline(Font.U_SINGLE);
                hyperlinkFont.setColor(IndexedColors.BLUE.getIndex());
                hyperlinkStyle.setFont(hyperlinkFont);

                CreationHelper createHelper = cellNew.getSheet().getWorkbook().getCreationHelper();
                Hyperlink link = createHelper.createHyperlink(Hyperlink.LINK_URL);
                link.setAddress(linkOld.getAddress());
                cellNew.setHyperlink(link);
                cellNew.setCellStyle(hyperlinkStyle);
            }
        } catch (IllegalArgumentException e) {

        }

        CellStyle newCellStyle;
        newCellStyle = cellNew.getSheet().getWorkbook().createCellStyle();

        //Создаём в новом документе тот же стиль ячейкам, что был в xls-документе
        if (cellOld.getCellStyle() != null) {
            newCellStyle = cellNew.getSheet().getWorkbook().createCellStyle();
            newCellStyle.setBorderTop(cellOld.getCellStyle().getBorderTop());
            newCellStyle.setBorderLeft(cellOld.getCellStyle().getBorderLeft());
            newCellStyle.setBorderRight(cellOld.getCellStyle().getBorderRight());
            newCellStyle.setBorderBottom(cellOld.getCellStyle().getBorderBottom());
            newCellStyle.setDataFormat(cellOld.getCellStyle().getDataFormat());
            //Обращаемся к методу конвертирования шрифта.
            try {
                newCellStyle.setFont(this.convertFont(cellNew.getSheet().getWorkbook(), cellOld.getCellStyle().getFont(cellOld.getRow().getSheet().getWorkbook())));
            } catch (ClassCastException e) {
            }
        }
        newCellStyle.setWrapText(true);
        cellNew.setCellStyle(newCellStyle);

        switch (cellOld.getCellType()) {
            case Cell.CELL_TYPE_BLANK:
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                cellNew.setCellValue(cellOld.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_ERROR:
                cellNew.setCellValue(cellOld.getErrorCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
                cellNew.setCellFormula(cellOld.getCellFormula());
                break;
            case Cell.CELL_TYPE_NUMERIC:
                cellNew.setCellValue(cellOld.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING:
                cellNew.setCellValue(cellOld.getStringCellValue());
                break;
            default:
                System.out.println("Unknown cell type: " + cellOld.getCellType());
        }
    }

    /**
     * Назначение стиля текста в ячейках
     *
     * @param workbookNew - книга xlsx
     * @param fontOld     - стиль текста в xls
     * @return стиль текста ячеек для xlsx
     */
    private XSSFFont convertFont(XSSFWorkbook workbookNew, HSSFFont fontOld) {
        XSSFFont fontNew = workbookNew.createFont();
        try {
            fontNew.setBoldweight(fontOld.getBoldweight());
            fontNew.setCharSet(fontOld.getCharSet());
            fontNew.setColor(fontOld.getColor());
            fontNew.setFontName(fontOld.getFontName());
            fontNew.setFontHeight(fontOld.getFontHeight());
            fontNew.setItalic(fontOld.getItalic());
            fontNew.setStrikeout(fontOld.getStrikeout());
            fontNew.setTypeOffset(fontOld.getTypeOffset());
            fontNew.setUnderline(fontOld.getUnderline());
        } catch (Exception e) {
            return defaultFont(workbookNew);
        }

        return fontNew;
    }

    private XSSFFont defaultFont(XSSFWorkbook workbookNew) {
        XSSFFont font = workbookNew.createFont();
        font.setBoldweight((short) 700);
        font.setCharSet(204);
        font.setColor((short) 32767);
        font.setFontName("Times New Roman");
        font.setFontHeight(200);
        font.setItalic(false);
        font.setStrikeout(false);
        font.setTypeOffset((short) 0);
        font.setUnderline((byte) 0);
        return font;
    }
}
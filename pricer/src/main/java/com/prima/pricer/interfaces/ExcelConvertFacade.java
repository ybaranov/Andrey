package com.prima.pricer.interfaces;

public interface ExcelConvertFacade {

    /**
     * Метод обходит все книги в заданой директории, и если книга в формате xls -
     * ее конвертируем в формата xlsx
     */
    void prepareAllBooks();

}
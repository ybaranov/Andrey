package com.prima.pricer;

import com.prima.pricer.interfaces.ExcelConvertFacade;
import com.prima.pricer.service.CatalogService;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Ignore
public class ExcelConvertServiceTest extends AbstractTest {
    private static ExcelConvertFacade converterService;
    private static Path path;

    @SuppressWarnings("resource")
    private static void beforeTest() {
        converterService = (ExcelConvertFacade) applicationContext.getBean("excelConvertService");

        Properties prop = new Properties();
        InputStream input = null;
        try {
            String filename = "application.properties";
            input = CatalogService.class.getClassLoader().getResourceAsStream(filename);
            prop.load(input);
            path = Paths.get(prop.getProperty("root.folder") + "prices\\");
        } catch (IOException e) {
            logger.warn("Exception into reading properties file application.properties");
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    logger.warn("Can't close FileInputStream for file application.properties");
                }
            }
        }
    }

    @Test
    public void test() throws IOException {
        //arrange
        beforeClass();
        beforeTest();

        //act
        converterService.prepareAllBooks();

        //arrange
        List<Path> pathsToXls = Files.walk(path)
                .filter(Files::isRegularFile)
                .filter(path1 -> toString().endsWith(".xls"))
                .collect(Collectors.toList());
        List<Path> pathsToXlsx = Files.walk(path)
                .filter(Files::isRegularFile)
                .filter(path1 -> toString().endsWith(".xlsx"))
                .collect(Collectors.toList());

        //assert
        assertTrue(pathsToXlsx.containsAll(pathsToXls));
    }
}
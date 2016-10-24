package com.prima.pricer.service;

import com.prima.configuration.dom.Root;
import com.prima.pricer.interfaces.CatalogFacade;
import com.prima.pricer.model.ObjectToProcessing;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CatalogService extends AbstractService implements CatalogFacade {

    protected Path path;

    public CatalogService() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            String filename = "application.properties";
            input = CatalogService.class.getClassLoader().getResourceAsStream(filename);
            prop.load(input);
            path = Paths.get(prop.getProperty("root.folder"));
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

    @Override
    public List<ObjectToProcessing> selectNewObjects() {
        List<Path> pathsExcel = null;
        Set<String> pathsXml = null;
        List<ObjectToProcessing> result = new ArrayList<>();
        try {
            Path excelPath = Paths.get(path.toString() + "\\prices\\");
            Path xmlPath = Paths.get(path.toString() + "\\configs\\");
            pathsExcel = Files.walk(excelPath)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".xlsx"))
                    .collect(Collectors.toList());
            if (pathsExcel == null) {
                logger.warn("No one new Excel file was added");
                return null;
            }
            pathsXml = Files.walk(xmlPath)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".xml"))
                    .map(path -> path.toString())
                    .collect(Collectors.toSet());
            if (pathsXml == null) {
                logger.warn("No one new XML file was added");
                return null;
            }
        } catch (IOException e) {
            logger.warn("Can't collect paths from Path path");
        }

        for (Path path : pathsExcel) {
            String fileExcel = path.toString();
            int index = getIndexOfFile(fileExcel);
            String fileXml = fileExcel
                    .replaceAll("p" + index, "c" + index)
                    .replaceFirst("prices", "configs")
                    .substring(0, fileExcel.length() - 4)
                    + ".xml";
            if (pathsXml.contains(fileXml)) {
                ObjectToProcessing obj = new ObjectToProcessing();
                obj.setPathToExcel(fileExcel);
                obj.setPathToConfigXML(fileXml);
                try {
                    File file = new File(fileXml);
                    JAXBContext jaxbContext = JAXBContext.newInstance(Root.class);
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    Root root = (Root) jaxbUnmarshaller.unmarshal(file);
                    obj.setRoot(root);
                } catch (JAXBException e) {
                    logger.warn("Can't get Root class instance from XML");
                }
                result.add(obj);
            }
        }
        return result;
    }

    public int getIndexOfFile(String path) {
        Pattern pattern = Pattern.compile("(p[0-9]{1,2})");
        Matcher matcher = pattern.matcher(path);
        matcher.find();
        String group = matcher.group(1);
        return Integer.parseInt(group.substring(1));
    }
}
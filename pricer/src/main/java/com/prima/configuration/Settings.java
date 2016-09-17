package com.prima.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;

public class Settings {

    private Properties properties;

    private static Settings instance;

    private com.prima.configuration.dom.Root root;

    public static Settings instance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    public static com.prima.configuration.dom.Root instanceXml() {
        return instance().root;
    }

    private Settings() {
        //readProperties();
        try {
            readXml();
        } catch (JAXBException | SAXException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void readXml() throws JAXBException, SAXException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(getClass().getClassLoader().getResource("price.xsd"));

        InputStream is = getClass().getClassLoader().getResourceAsStream("price_conf_0.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(com.prima.configuration.dom.Root.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        jaxbUnmarshaller.setSchema(schema);
        root = (com.prima.configuration.dom.Root) jaxbUnmarshaller.unmarshal(is);
    }
}

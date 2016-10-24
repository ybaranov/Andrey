package com.prima.pricer.service;

import com.prima.configuration.dom.Root;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.InputStream;

public class XmlConfigService extends AbstractService {

    @SuppressWarnings("restriction")
    public Root readConfObject(String fileName) {
        try {
            return readXml(fileName);
        } catch (JAXBException | SAXException ex) {
            logger.error("error when getting xml from file: " + fileName + ".", ex);
            return null;
        }
    }

    @SuppressWarnings("restriction")
    private Root readXml(String fileName) throws JAXBException, SAXException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(getClass().getClassLoader().getResource("price.xsd"));

        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
        JAXBContext jaxbContext = JAXBContext.newInstance(com.prima.configuration.dom.Root.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        jaxbUnmarshaller.setSchema(schema);
        return (Root) jaxbUnmarshaller.unmarshal(is);
    }
}

package com.appdirect.integration.utils;

import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;

@Component
public class EventParser {

    public <T> T parse(InputStream content, Class<T> eventClass) throws JAXBException, IOException {

        JAXBContext context = JAXBContext.newInstance(eventClass);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (T) unmarshaller.unmarshal(content);
    }
}

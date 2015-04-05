package com.appdirect.integration.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;

@Component
public class EventParser {

    private static Logger logger = LoggerFactory.getLogger(EventParser.class);

    public <T> T parse(InputStream content, Class<T> eventClass) throws JAXBException, IOException {

        logContent(content);
        content.reset();
        JAXBContext context = JAXBContext.newInstance(eventClass);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (T) unmarshaller.unmarshal(content);
    }

    private void logContent(InputStream content) throws UnsupportedEncodingException {
        final char[] buffer = new char[1024];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(content, "UTF-8");
        try {
            for (; ; ) {
                int rsz = in.read(buffer, 0, buffer.length);
                if (rsz < 0)
                    break;
                out.append(buffer, 0, rsz);
            }
        } catch (IOException ignored) {

        }
        logger.info(out.toString());
    }
}

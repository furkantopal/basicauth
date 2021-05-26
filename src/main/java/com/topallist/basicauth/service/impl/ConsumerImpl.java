package com.topallist.basicauth.service.impl;

import com.topallist.basicauth.service.Client;
import com.topallist.basicauth.service.Consumer;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConsumerImpl implements Consumer {

  @Autowired
  Client client;

  @Override
  public Object getApiResponse(String uri, Class xmlClass)
      throws IOException, JAXBException, XMLStreamException {

    HttpEntity respond = client.getEntity(uri);

    JAXBContext jaxbContext = JAXBContext.newInstance(xmlClass);
    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

    XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
    xmlInputFactory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
    xmlInputFactory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
    xmlInputFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, false);
    XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(respond.getContent());

    return unmarshaller.unmarshal(xmlStreamReader);
  }
}

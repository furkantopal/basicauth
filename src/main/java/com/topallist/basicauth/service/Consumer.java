package com.topallist.basicauth.service;

import java.io.IOException;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

public interface Consumer {

  Object getApiResponse(String uri, Class xmlClass)
      throws IOException, JAXBException, XMLStreamException;

}

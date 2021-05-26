package com.topallist.basicauth.service;

import java.io.IOException;
import org.apache.http.HttpEntity;

public interface Client {

  String getBasicAuthentication() throws IOException;

  String getAuthHeader();

  HttpEntity getEntity(String uri) throws IOException;
}

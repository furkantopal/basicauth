package com.topallist.basicauth.service.impl;

import static com.topallist.basicauth.utils.Constants.BASIC_AUTH_HEADER_PREFIX;
import static com.topallist.basicauth.utils.Constants.COLON;
import static org.apache.http.HttpHeaders.ACCEPT;
import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;

import com.topallist.basicauth.config.ApiProperties;
import com.topallist.basicauth.service.Client;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ClientImpl implements Client {

  @Autowired
  ApiProperties apiProperties;
  String token;

  @PostConstruct
  private void postConstruct() throws IOException {
    token = getBasicAuthentication();
  }

  @Override
  public String getBasicAuthentication() throws IOException {

    HttpPost postRequest = new HttpPost(apiProperties.getUri());
    postRequest.setHeader(ACCEPT, apiProperties.getHeaderAccept());
    postRequest.setHeader(CONTENT_TYPE, apiProperties.getHeaderContentType());
    postRequest.setHeader(AUTHORIZATION, getAuthHeader());

    CloseableHttpClient client = HttpClientBuilder.create().build();
    CloseableHttpResponse response = client.execute(postRequest);

    log.info("Api Basic Authentication established with a token received.");
    return response.getFirstHeader(apiProperties.getHeaderToken()).getValue();
  }

  @Override
  public String getAuthHeader() {

    String auth = apiProperties.getUserName().concat(COLON).concat(apiProperties.getPassword());
    byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.ISO_8859_1));
    return BASIC_AUTH_HEADER_PREFIX.concat(new String(encodedAuth));
  }

  @Override
  public HttpEntity getEntity(String uri) throws IOException {

    HttpGet getRequest = new HttpGet(uri);
    getRequest.setHeader(ACCEPT, apiProperties.getHeaderAccept());
    getRequest.setHeader(CONTENT_TYPE, apiProperties.getHeaderContentType());
    getRequest.setHeader(apiProperties.getHeaderToken(), token);

    CloseableHttpClient client = HttpClientBuilder.create().build();
    CloseableHttpResponse response = client.execute(getRequest);

    return response.getEntity();
  }
}

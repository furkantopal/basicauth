package com.topallist.basicauth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "api")
@Data
public class ApiProperties {

  private String uri;
  private String userName;
  private String password;
  private String headerAccept;
  private String headerContentType;
  private String headerToken;
}

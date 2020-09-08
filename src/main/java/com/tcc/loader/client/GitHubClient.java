package com.tcc.loader.client;

import java.time.LocalDate;

import com.tcc.loader.dto.GitHubFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GitHubClient {

  private static final Logger logger = LoggerFactory.getLogger(GitHubClient.class);
  private static final String GITHUB_JSON = "application/vnd.github.v3+json";

  private RestTemplate restTemplate = new RestTemplate();

  @Value("${github.personal.token}")
  private String gitPersonalToken;

  public void uploadFile(GitHubFile file) {
    HttpEntity<GitHubFile> entity = getHttpEntityWithHeaders(file);
    String apiUrl = getDirectoryApiUrl(file.getFileName());

    restTemplate.exchange(apiUrl, HttpMethod.PUT, entity, String.class);
    logger.info("Uploaded file {}", file.getFileName());
  }

  private HttpEntity<GitHubFile> getHttpEntityWithHeaders(GitHubFile file) {
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.ACCEPT, GITHUB_JSON);
    headers.set(HttpHeaders.AUTHORIZATION, "token " + gitPersonalToken);
    return new HttpEntity<GitHubFile>(file, headers);
  }

  private String getDirectoryApiUrl(String fileName) {
    StringBuilder sb = new StringBuilder();
    return sb.append("https://api.github.com/repos/")
      .append("willianba") // TODO remove hadcoded user
      .append("/")
      .append("translated-files") // TODO remove harcoded repo
      .append("/contents/imperative/")
      .append(LocalDate.now())
      .append("/")
      .append(fileName)
      .toString();
  }
}

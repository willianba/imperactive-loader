package com.tcc.loader.service;

import com.tcc.loader.client.GitHubClient;
import com.tcc.loader.dto.GitHubFile;
import com.tcc.loader.dto.TranslatedFile;
import com.tcc.loader.helper.Converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoadService {

  private static final Logger logger = LoggerFactory.getLogger(LoadService.class);

  @Autowired
  private GitHubClient client;

  @Autowired
  private Converter converter;

  public void uploadFileToGitHub(TranslatedFile file) {
    logger.info("Uploading file {} on GitHub", file.getFileName());
    GitHubFile githubFile = converter.convertToGitHubFile(file);
    client.uploadFile(githubFile);
  }
}

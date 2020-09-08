package com.tcc.loader.rabbitmq.subscriber;

import java.util.List;

import com.tcc.loader.dto.TranslatedFile;
import com.tcc.loader.service.LoadService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GitHubContentSubscriber {

  private static final Logger logger = LoggerFactory.getLogger(GitHubContentSubscriber.class);

  @Autowired
  private LoadService service;

  @RabbitListener(queues = "${translated.queue}")
  public void receiveTranslatedFiles(List<TranslatedFile> files) {
    logger.info("Received {} translated files to be uploaded", files.size());
    service.uploadFilesToGitHub(files);
  }
}

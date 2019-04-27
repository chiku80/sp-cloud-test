package com.example.producer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(OpJobOracleBinding.class)
public class TestConsumer {

  private static final Logger log = LoggerFactory.getLogger(TestConsumer.class);

  @StreamListener(OpJobOracleBinding.ORACLE_EVENTS_TARGET)
  public void processOracleEvent(String opJobString, @Headers MessageHeaders headers) {
    try {
      log.info("Headers received: " + headers);
      log.info("Received event from binding:" + opJobString);
    } catch (Exception e) {
      log.error("error");
    }
  }



}

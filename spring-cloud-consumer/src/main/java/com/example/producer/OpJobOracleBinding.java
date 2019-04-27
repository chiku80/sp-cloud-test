package com.example.producer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OpJobOracleBinding {

  String ORACLE_EVENTS_TARGET = "oracleOpJobEvents";

  @Input
  MessageChannel oracleOpJobEvents();


}

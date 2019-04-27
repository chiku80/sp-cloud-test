package com.example.producer;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.amazonaws.services.kinesis.model.DescribeStreamRequest;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.amazonaws.services.kinesis.model.PutRecordResult;
import com.amazonaws.services.kinesis.model.ResourceNotFoundException;
import com.amazonaws.services.kinesis.model.StreamDescription;
import java.nio.ByteBuffer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class TestProducer implements CommandLineRunner {

  @Value("${test.region}")
  String region;

  @Value("${test.awsAccessKeyId}")
  String awsAccessKeyId;


  @Value("${test.awsSecretSharedKey}")
  String awsSecretSharedKey;

  @Value("${test.queueName}")
  String queueName;


  @Override
  public void run(String... args) throws Exception {

    AmazonKinesis kinesis = getKinesisClient(region, awsAccessKeyId, awsSecretSharedKey);

    int count = 0;

    while (true) {

      long createTime = System.currentTimeMillis();

      DescribeStreamRequest describeStreamRequest = new DescribeStreamRequest()
          .withStreamName(queueName);
      try {
        StreamDescription streamDescription = kinesis.describeStream(describeStreamRequest)
            .getStreamDescription();
        System.out.printf("Stream %s has a status of %s.\n", queueName,
            streamDescription.getStreamStatus());
      } catch (ResourceNotFoundException ex) {
        System.out.printf("Stream %s does not exist. bummer.\n", queueName);
      }

      String messageToSend = "Test Message";

      PutRecordRequest putRecordRequest = new PutRecordRequest();
      putRecordRequest.setStreamName(queueName);
      putRecordRequest.setData(ByteBuffer.wrap(messageToSend.getBytes()));
      putRecordRequest.setPartitionKey("default");
      PutRecordResult putRecordResult = kinesis.putRecord(putRecordRequest);
      System.out.printf(
          "Successfully put record, partition key : %s, ShardID : %s, SequenceNumber : %s.\n",
          putRecordRequest.getPartitionKey(), putRecordResult.getShardId(),
          putRecordResult.getSequenceNumber());

      Thread.sleep(1000);
      count++;

    }


  }


  public static AmazonKinesis getKinesisClient(String region, String awsAccessKeyId,
      String awsSecretSharedKey) {

    AWSCredentialsProvider credentials = new AWSStaticCredentialsProvider(
        new BasicAWSCredentials(awsAccessKeyId, awsSecretSharedKey));

    return AmazonKinesisClientBuilder.standard().withCredentials(credentials).withRegion(region)
        .build();

  }


}

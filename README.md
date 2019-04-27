# SP-CLOUD-TEST 

This directory contains 2 project directories:
1. thirdparty-producer (This uses regular aws java sdk to produce to kinesis)
2. spring-cloud-consumer  (This is uses spring-cloud-stream to consume)

# Pre-requsites: 
1. jdk 11.0 
2. maven 3.6.0
3. Need aws account information with admin priveiledges to aws kinesis


# How to test 

## Configuration
configure your aws values in the following files. Configure the properties  `<yourkey>` `<your secret>` and `<your kinesis queue name>`
                                                                              
1. thirdparty-producer/src/main/resources/application-yml
2. spring-cloud-consumer/src/main/resources/application-yml



## Run producer
In a command line window run the commands:
1. `cd thirdparty-producer`
2. mvn spring-boot:run

## Run consumer
In a sperate command line window run the commands:
1. `cd spring-cloud-consumer`
2. mvn spring-boot:run


## Results
You will see that the producer is producing and the consumer is consuming. In the consumer window you will see the following log statments:
```
Received event from binding:Test Message
2019-04-27 14:26:59.935  INFO 88235 --- [esis-consumer-1] com.example.producer.TestConsumer        : Headers received: {aws_shard=shardId-000000000000, id=91b27ecb-1b6f-ffe8-d629-e9db8a942632, contentType=text/plain, aws_receivedPartitionKey=default, aws_receivedStream=bdata-test-oracle-opjob, aws_receivedSequenceNumber=49595094626030123609261409294557697561813201785160990722, timestamp=1556400419935}

```

Note that the consumer logs show that i am infact receiveing the header `aws_receivedSequenceNumber`
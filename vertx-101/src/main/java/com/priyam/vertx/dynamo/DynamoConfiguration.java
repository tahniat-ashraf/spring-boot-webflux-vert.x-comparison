package com.priyam.vertx.dynamo;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

import java.net.URI;

/**
 * @author Tahniat Ashraf Priyam
 * @since 3/14/21
 */

public class DynamoConfiguration {

  public DynamoDbEnhancedAsyncClient getDynamoDBEnhancedClient() {

    return DynamoDbEnhancedAsyncClient.builder()
      .dynamoDbClient(getDynamoDbClient())
      .build();
  }

  public DynamoDbAsyncClient getDynamoDbClient() {


    return DynamoDbAsyncClient.builder()
      .endpointOverride(URI.create("http://dynamodb.host:4566"))
      .region(Region.AP_SOUTHEAST_1)
      .build();
  }
}

package com.bkash.priyam.keyvalue.dynamo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

import java.net.URI;

/**
 * @author Tahniat Ashraf Priyam
 * @since 3/21/21
 */

@Configuration
public class DynamoConfiguration {

    @Bean
    public DynamoDbEnhancedAsyncClient getDynamoDBEnhancedClient() {

        return DynamoDbEnhancedAsyncClient.builder()
                .dynamoDbClient(getDynamoDbClient())
                .build();
    }

    @Bean
    public DynamoDbAsyncClient getDynamoDbClient() {


        return DynamoDbAsyncClient.builder()
                .endpointOverride(URI.create("http://dynamodb.host:4566"))
                .region(Region.AP_SOUTHEAST_1)
                .build();
    }
}

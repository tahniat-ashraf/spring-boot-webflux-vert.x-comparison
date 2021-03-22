package com.bkash.priyam.keyvalue.dynamo;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PagePublisher;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;

import java.util.concurrent.CompletableFuture;

/**
 * @author Tahniat Ashraf Priyam
 * @since 3/21/21
 */

public abstract class AbstractDynamoRepository<T> {

    private final DynamoDbEnhancedAsyncClient enhancedClient;
    private final DynamoDbAsyncTable<T> mappedTable;


    public AbstractDynamoRepository(String tableName, DynamoDbEnhancedAsyncClient dynamoDbEnhancedClient, Class<T> tClass) {
        this.enhancedClient = dynamoDbEnhancedClient;
        this.mappedTable = enhancedClient.table(tableName, TableSchema.fromBean(tClass));
    }

    public CompletableFuture<T> getItemWithPartitionKey(String partitionKey) {

        Key key = Key.builder()
                .partitionValue(partitionKey)
                .build();

        // Get the item by using only the partitionKey
        return mappedTable.getItem(r -> r.key(key));

    }

    public CompletableFuture<T> getItemWithPartitionAndSortKey(String partitionKey, String sortKey) {

        Key key = Key.builder()
                .partitionValue(partitionKey)
                .sortValue(sortKey)
                .build();

        // Get the item by using both the partitionKey key & sortKey
        return mappedTable.getItem(r -> r.key(key));

    }

    public PagePublisher<T> queryItemWithOnlyPartitionKey(String partitionKey) {


        var queryConditional = QueryConditional
                .keyEqualTo(Key.builder().partitionValue(partitionKey)
                        .build());

        return mappedTable.query(r -> r.queryConditional(queryConditional));


    }

    public PagePublisher<T> queryItemWithOnlyPartitionKeySortedInDescOrder (String partitionKey) {


        var queryConditional = QueryConditional
                .keyEqualTo(Key.builder().partitionValue(partitionKey)
                        .build());

        return mappedTable.query(r -> r.queryConditional(queryConditional).scanIndexForward(false));


    }

    public CompletableFuture<Void> saveItem(T t) {

        return mappedTable.putItem(t);
    }

    public PagePublisher<T> findAllItems() {

        return mappedTable.scan(ScanEnhancedRequest.builder().consistentRead(true).build());
    }

    public CompletableFuture<T> deleteItem(T t) {

        return mappedTable.deleteItem(t);
    }

}


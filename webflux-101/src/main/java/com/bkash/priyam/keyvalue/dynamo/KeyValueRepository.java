package com.bkash.priyam.keyvalue.dynamo;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.model.PagePublisher;

/**
 * @author Tahniat Ashraf Priyam
 * @since 3/21/21
 */

@Repository
public class KeyValueRepository extends AbstractDynamoRepository<KeyValue> {


    public KeyValueRepository(DynamoDbEnhancedAsyncClient dynamoDbEnhancedClient) {

        super("dev-paybill-key-value", dynamoDbEnhancedClient, KeyValue.class);
    }

    public Flux<KeyValue> getAllKeyValues() {

        return Flux.from(findAllItems().items());

    }

    public Mono<Void> saveKeyValue(KeyValue keyValue) {
        return Mono.fromFuture(saveItem(keyValue));
    }

    public Mono<KeyValue> getKeyValue(String key) {

        return Mono.fromFuture(getItemWithPartitionKey(key));
    }

    public Mono<KeyValue> deleteKeyValue(String key) {
        return Mono.fromFuture(deleteItem(KeyValue.builder().key(key).build()));
    }
}

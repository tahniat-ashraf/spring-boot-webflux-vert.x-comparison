package com.priyam.vertx.dynamo;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.model.PagePublisher;


/**
 * @author Tahniat Ashraf Priyam
 * @since 3/13/21
 */

public class KeyValueRepository extends AbstractDynamoRepository<KeyValue> {


  public KeyValueRepository(String tableName, DynamoDbEnhancedAsyncClient dynamoDbEnhancedClient) {
    super(tableName, dynamoDbEnhancedClient, KeyValue.class);

  }

  public PagePublisher<KeyValue> getAllKeyValues() {

    return findAllItems();

  }

  public Completable saveKeyValue(KeyValue keyValue) {
    return Completable.fromFuture(saveItem(keyValue));
  }

  public Single<KeyValue> getKeyValue(String key) {

    return Single.fromFuture(getItemWithPartitionKey(key));
  }

  public Observable<KeyValue> deleteKeyValue(KeyValue keyValue) {
    return Observable.fromFuture(deleteItem(keyValue));
  }

}


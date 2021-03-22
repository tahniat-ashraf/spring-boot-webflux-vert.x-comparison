package com.priyam.vertx.dynamo;


import io.reactivex.Observable;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * @author Tahniat Ashraf Priyam
 * @since 3/13/21
 */

public class KeyValueServiceVerticle extends AbstractVerticle {

  public static final String GET_LIST_ADDRESS = "GET_LIST_KEY_VAL";
  public static final String SAVE_ADDRESS = "SAVE_KEY_VAL";
  public static final String GET_ADDRESS = "GET_KEY_VAL";
  public static final String DELETE_ADDRESS = "DELETE_KEY_VAL";
  private KeyValueRepository keyValueRepository;
  private DynamoConfiguration dynamoConfiguration;

  @Override
  public void start() throws Exception {

    dynamoConfiguration = new DynamoConfiguration();
    keyValueRepository = new KeyValueRepository("dev-paybill-key-value", dynamoConfiguration.getDynamoDBEnhancedClient());

    var eventBus = vertx.eventBus();
    eventBus
      .consumer(KeyValueServiceVerticle.GET_ADDRESS, this::getKeyValue);

    eventBus
      .consumer(KeyValueServiceVerticle.GET_LIST_ADDRESS, this::getKeyValues);

    eventBus
      .consumer(KeyValueServiceVerticle.DELETE_ADDRESS, this::deleteKeyValue);

    eventBus
      .consumer(KeyValueServiceVerticle.SAVE_ADDRESS, this::saveKeyValue);

  }

  private <T> void saveKeyValue(Message<T> tMessage) {
    var jsonObjectX = (JsonObject) tMessage.body();
    var newKeyValue = jsonObjectX.mapTo(KeyValue.class);
    keyValueRepository.saveKeyValue(newKeyValue)
      .subscribe(() -> tMessage.reply(jsonObjectX),
        Throwable::printStackTrace);
  }

  private <T> void deleteKeyValue(Message<T> tMessage) {
    keyValueRepository.deleteKeyValue(KeyValue.builder().key(tMessage.body().toString()).build())
      .subscribe(keyValue -> {
        var jsonObject = JsonObject.mapFrom(keyValue);
        tMessage.reply(jsonObject);
      });
  }

  private <T> void getKeyValues(Message<T> tMessage) {

    Observable.fromPublisher(keyValueRepository.findAllItems().items())
      .toList()
      .subscribe(tList -> {
        System.out.println("tList = " + tList);
        JsonArray jsonArray=new JsonArray(tList);
        tMessage.reply(jsonArray.encodePrettily());
      });

  }

  private <T> void getKeyValue(Message<T> tMessage) {
    keyValueRepository.getKeyValue(tMessage.body().toString())
      .onErrorReturn(throwable -> KeyValue.builder().build())
      .subscribe(keyValue -> {
        var jsonObject = JsonObject.mapFrom(keyValue);
        tMessage.reply(jsonObject);
      });
  }


}

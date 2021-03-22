package com.priyam.vertx.dynamo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.LoggerFormat;
import io.vertx.ext.web.handler.LoggerHandler;

/**
 * @author Tahniat Ashraf Priyam
 * @since 3/14/21
 */

public class KeyValueController extends AbstractVerticle {

  @Override
  public void start() throws Exception {

    Router router = Router.router(vertx);

    router
      .route()
      .handler(BodyHandler.create());

    router.route()
      .handler(LoggerHandler.create(LoggerFormat.DEFAULT));

    router
      .route(HttpMethod.GET, "/keyValues/:key")
      .handler(this::getKeyValue);

    router
      .route(HttpMethod.GET, "/keyValues")
      .handler(this::getKeyValues);

    router
      .route(HttpMethod.DELETE,"/keyValues/:key")
      .handler(this::deleteKeyValue);

    router
      .route(HttpMethod.POST,"/keyValues")
      .handler(this::saveKeyValue);

    vertx
      .createHttpServer()
      .requestHandler(router)
      .listen(6678);

  }

  private void saveKeyValue(RoutingContext routingContext) {

    var jsonObject = routingContext.getBodyAsJson();
    vertx
    .eventBus()
      .request(KeyValueServiceVerticle.SAVE_ADDRESS, jsonObject, messageAsyncResult ->
        routingContext.response()
          .putHeader("content-type", "application/json")
          .end(Json.encodePrettily(messageAsyncResult.result().body()))
      );  }

  private void deleteKeyValue(RoutingContext routingContext) {
    var key = routingContext.pathParam("key");
    vertx
      .eventBus()
      .request(KeyValueServiceVerticle.DELETE_ADDRESS, key, messageAsyncResult ->
        routingContext.response()
          .putHeader("content-type", "application/json")
          .end(Json.encodePrettily(messageAsyncResult.result().body()))
      );
  }


  private void getKeyValue(RoutingContext routingContext) {

    var key = routingContext.pathParam("key");
    vertx
      .eventBus()
      .request(KeyValueServiceVerticle.GET_ADDRESS, key, messageAsyncResult ->
        routingContext.response()
          .putHeader("content-type", "application/json")
          .end(Json.encodePrettily(messageAsyncResult.result().body()))
      );
  }

  private void getKeyValues(RoutingContext routingContext) {

    vertx
      .eventBus()
      .request(KeyValueServiceVerticle.GET_LIST_ADDRESS, new JsonObject(), messageAsyncResult ->
        routingContext.response()
          .putHeader("content-type", "application/json")
          .end((String) messageAsyncResult.result().body())
      );
  }
}

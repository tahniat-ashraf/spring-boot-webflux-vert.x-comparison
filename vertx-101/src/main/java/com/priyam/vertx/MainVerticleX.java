package com.priyam.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;

public class MainVerticleX extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    DeploymentOptions options = new DeploymentOptions().setInstances(4);
    vertx.deployVerticle("com.priyam.vertx.dynamo.KeyValueServiceVerticle",options);
    vertx.deployVerticle("com.priyam.vertx.dynamo.KeyValueController",options);

  }
}

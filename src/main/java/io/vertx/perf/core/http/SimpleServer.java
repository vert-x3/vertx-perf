package io.vertx.perf.core.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class SimpleServer extends AbstractVerticle {

  public void start() {
    HttpServer server = vertx.createHttpServer();
    server.requestHandler(req -> {
      req.response().end();
    }).listen(8080);
  }
}

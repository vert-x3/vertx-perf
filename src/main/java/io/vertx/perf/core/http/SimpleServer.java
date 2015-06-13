package io.vertx.perf.core.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class SimpleServer extends AbstractVerticle {

  public void start() {
    HttpServer server = vertx.createHttpServer();
    String host = System.getProperty("vertx.host", "localhost");
    int port = Integer.getInteger("vertx.port", 8080);
    System.out.println("Host: " + host);
    System.out.println("Port: " + port);
    server.requestHandler(req -> {
      req.response().end();
    }).listen(port, host);
  }
}

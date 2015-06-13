package io.vertx.perf.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class SimpleStatic extends AbstractVerticle {

  public void start() {

    String host = System.getProperty("vertx.host", "localhost");
    int port = Integer.getInteger("vertx.port", 8080);
    System.out.println("Host: " + host);
    System.out.println("Port: " + port);

    Router router = Router.router(vertx);

    // Serve the static pages
    router.route().handler(StaticHandler.create());

    vertx.createHttpServer().requestHandler(router::accept).listen(port, host);

    System.out.println("Server is started");
  }
}

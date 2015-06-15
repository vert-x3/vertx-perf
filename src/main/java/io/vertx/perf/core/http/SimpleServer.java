package io.vertx.perf.core.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class SimpleServer extends AbstractVerticle {

  private final Buffer helloWorldBuffer = Buffer.buffer("Hello, World!");
  private final CharSequence helloWorldContentLength = HttpHeaders.createOptimized(String.valueOf(helloWorldBuffer.length()));
  private final DateFormat DATE_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyyy HH:mm:ss z");

  private static final CharSequence HEADER_SERVER_VERTX = HttpHeaders.createOptimized("vert.x");
  private static final CharSequence RESPONSE_TYPE_PLAIN = HttpHeaders.createOptimized("text/plain");

  private String dateString;


  private void setHeaders(HttpServerResponse resp) {
    resp.putHeader(HttpHeaders.CONTENT_TYPE, RESPONSE_TYPE_PLAIN);
    resp.putHeader(HttpHeaders.CONTENT_LENGTH, helloWorldContentLength);
    resp.putHeader(HttpHeaders.SERVER, HEADER_SERVER_VERTX );
    resp.putHeader(HttpHeaders.DATE, dateString);
  }

  private void formatDate() {
    dateString = DATE_FORMAT.format(new Date());
  }

  public void start() {
    vertx.setPeriodic(1000, tid -> formatDate());
    formatDate();
    HttpServer server = vertx.createHttpServer();
    String host = System.getProperty("vertx.host", "localhost");
    int port = Integer.getInteger("vertx.port", 8080);
    System.out.println("Host: " + host);
    System.out.println("Port: " + port);
    server.requestHandler(req -> {
      HttpServerResponse resp = req.response();
      setHeaders(resp);
      resp.end(helloWorldBuffer);
    }).listen(port, host);
  }
}

package io.vertx.perf.core.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
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
  private final String helloWorldContentLength = String.valueOf(helloWorldBuffer.length());
  private final DateFormat DATE_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyyy HH:mm:ss z");
  private String dateString;

  private static final String HEADER_CONTENT_TYPE = "Content-Type";
  private static final String HEADER_CONTENT_LENGTH = "Content-Length";
  private static final String HEADER_SERVER = "Server";
  private static final String HEADER_SERVER_VERTX = "vert.x";
  private static final String HEADER_DATE = "Date";

  private static final String RESPONSE_TYPE_PLAIN = "text/plain";

  private void setHeaders(HttpServerResponse resp, String contentType, String contentLength) {
    resp.putHeader(HEADER_CONTENT_TYPE, contentType);
    resp.putHeader(HEADER_CONTENT_LENGTH, contentLength);
    resp.putHeader(HEADER_SERVER, HEADER_SERVER_VERTX );
    resp.putHeader(HEADER_DATE, dateString);
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
      setHeaders(resp, RESPONSE_TYPE_PLAIN, helloWorldContentLength);
      resp.end(helloWorldBuffer);
    }).listen(port, host);
  }
}

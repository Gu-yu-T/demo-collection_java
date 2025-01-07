package com.guyu.vertx;


import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainVerticle {

    public static void main(String[] args) {
        //创建 vert.x 实例
        Vertx vertx = Vertx.vertx();

        // 创建 http 服务器
        HttpServer server = vertx.createHttpServer();

        //配置http请求处理
        server.requestHandler(request -> {
            if (request.uri().equals("/")) {
                request.response().end("hello world !");
            } else if (request.uri().endsWith("/hello")) {
                request.response().end("hello from vert.x!");
            } else {
                request.response().setStatusCode(404).end("Not Found");
            }
        });

        //启动 http 服务器，监听 8080 端口
        server.listen(8080, res -> {
            if (res.succeeded()) {
                log.info("Server is now listening on port 8080!");
            } else {
                log.error("Failed to launch Server: " + res.cause());
            }
        });

    }
}

package com.ahmetduruer.portfolio

import io.vertx.core.AbstractVerticle
import io.vertx.core.http.HttpServerRequest

class Main : AbstractVerticle() {
    @Throws(Exception::class)
    override fun start() {
        vertx
                .createHttpServer()
                .requestHandler { req: HttpServerRequest ->
                    req.response().end("Hello World!")
                }
                .listen(Integer.getInteger("http.port", 8088), System.getProperty("http.address", "0.0.0.0"))
    }
}
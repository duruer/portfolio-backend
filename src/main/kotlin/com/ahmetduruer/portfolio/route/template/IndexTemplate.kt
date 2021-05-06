package com.ahmetduruer.portfolio.route.template

import com.ahmetduruer.portfolio.Main
import com.ahmetduruer.portfolio.model.Template
import io.vertx.core.Handler
import io.vertx.ext.web.RoutingContext

class IndexTemplate : Template() {
    private val mHotLinks =
        mapOf("/ref1" to "https://i.resmim.net/i/index-3.jpg", "/ref2" to "https://i.resmim.net/i/index-2-1.jpg")

    override val routes = arrayListOf("/*")

    override val order = 999

    override fun getHandler() = Handler<RoutingContext> { context ->
        val response = context.response()
        val normalisedPath = context.normalisedPath()

        if (!mHotLinks[normalisedPath.toLowerCase()].isNullOrEmpty())
            response.putHeader(
                    "location",
                    mHotLinks[normalisedPath.toLowerCase()]
            ).setStatusCode(302).end()
        else
            response.sendFile(Main.DEFAULT_PUBLIC_PATH + "index.html")
    }
}
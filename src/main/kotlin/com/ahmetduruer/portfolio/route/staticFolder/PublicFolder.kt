package com.ahmetduruer.portfolio.route.staticFolder

import com.ahmetduruer.portfolio.Main
import com.ahmetduruer.portfolio.model.Route
import io.vertx.ext.web.handler.impl.StaticHandlerImpl

class PublicFolder : Route() {
    override val routes = arrayListOf("/*")

    override fun getHandler() = StaticHandlerImpl(Main.DEFAULT_PUBLIC_PATH).setCachingEnabled(Main.ENVIRONMENT == Main.Companion.EnvironmentType.RELEASE)
}
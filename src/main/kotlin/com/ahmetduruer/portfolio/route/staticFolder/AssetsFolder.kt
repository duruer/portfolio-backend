package com.ahmetduruer.portfolio.route.staticFolder

import com.ahmetduruer.portfolio.Main
import com.ahmetduruer.portfolio.model.Route
import io.vertx.ext.web.handler.impl.StaticHandlerImpl

class AssetsFolder : Route() {
    override val routes = arrayListOf("/assets/*")

    override fun getHandler() = StaticHandlerImpl(Main.DEFAULT_PUBLIC_PATH + "assets").setCachingEnabled(Main.ENVIRONMENT == Main.Companion.EnvironmentType.RELEASE)
}
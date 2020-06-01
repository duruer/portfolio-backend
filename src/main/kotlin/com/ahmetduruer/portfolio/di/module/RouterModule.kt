package com.ahmetduruer.portfolio.di.module

import com.ahmetduruer.portfolio.model.Route
import com.ahmetduruer.portfolio.model.RouteType
import com.ahmetduruer.portfolio.model.Template
import com.ahmetduruer.portfolio.route.template.IndexTemplate
import dagger.Module
import dagger.Provides
import io.vertx.core.Vertx
import io.vertx.core.http.HttpMethod
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.CorsHandler
import io.vertx.ext.web.handler.SessionHandler
import io.vertx.ext.web.sstore.LocalSessionStore
import javax.inject.Singleton

@Module
class RouterModule(private val mVertx: Vertx) {
    @Singleton
    private val mRouter by lazy {
        val router = Router.router(mVertx)

        init(router)

        router
    }

    private val mStaticFolderRouteList by lazy {
        arrayOf<Route>(
        )
    }

    private val mTemplateRouteList by lazy {
        arrayOf<Template>(
                IndexTemplate()
        )
    }

    private val mRouteList by lazy {
        listOf(
                *mStaticFolderRouteList,
                *mTemplateRouteList
        )
    }

    private fun init(router: Router) {
        router.route().handler(BodyHandler.create())

        mRouteList.forEach { route ->
            route.routes.forEach { url ->
                when (route.routeType) {
                    RouteType.ROUTE -> router.route(url).order(route.order).handler(route.getHandler())
                            .failureHandler(route.getFailureHandler())
                    RouteType.GET -> router.get(url).order(route.order).handler(route.getHandler())
                            .failureHandler(route.getFailureHandler())
                    RouteType.POST -> router.post(url).order(route.order).handler(route.getHandler())
                            .failureHandler(route.getFailureHandler())
                    RouteType.DELETE -> router.delete(url).order(route.order).handler(route.getHandler())
                            .failureHandler(route.getFailureHandler())
                    RouteType.PUT -> router.put(url).order(route.order).handler(route.getHandler())
                            .failureHandler(route.getFailureHandler())
                }
            }
        }
    }

    @Provides
    @Singleton
    fun provideRouter() = mRouter

}
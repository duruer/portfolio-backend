package com.ahmetduruer.portfolio

import com.ahmetduruer.portfolio.di.component.ApplicationComponent
import com.ahmetduruer.portfolio.di.component.DaggerApplicationComponent
import com.ahmetduruer.portfolio.di.module.LoggerModule
import com.ahmetduruer.portfolio.di.module.RouterModule
import com.ahmetduruer.portfolio.di.module.VertxModule
import io.vertx.core.*
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.web.Router
import javax.inject.Inject

class Main : AbstractVerticle() {
    companion object {
        private val mOptions by lazy {
            VertxOptions()
        }
        private val mVertx by lazy {
            Vertx.vertx(mOptions)
        }
        private val mLogger = LoggerFactory.getLogger("Kahverengi Portfolio")

        val ENVIRONMENT =
            if (System.getenv("EnvironmentType").isNullOrEmpty())
                EnvironmentType.RELEASE
            else
                EnvironmentType.DEVELOPMENT

        const val PORT = 8088
        const val DEFAULT_PUBLIC_PATH = "public/"

        @JvmStatic
        fun main(args: Array<String>) {
            mVertx.deployVerticle(Main())
        }

        private lateinit var mComponent: ApplicationComponent

        internal fun getComponent() = mComponent

        enum class EnvironmentType {
            DEVELOPMENT, RELEASE
        }
    }

    @Inject
    lateinit var logger: Logger

    @Inject
    lateinit var router: Router

    override fun start(startPromise: Promise<Void>?) {
        vertx.executeBlocking<Any>({ future ->
            init().onComplete { init ->
                future.complete(init.result())
            }
        }, {
            startWebServer()
        })
    }

    private fun init() = Future.future<Boolean> { init ->
        mComponent = DaggerApplicationComponent
            .builder()
            .vertxModule(VertxModule(vertx))
            .loggerModule(LoggerModule(mLogger))
            .routerModule(RouterModule(vertx))
            .build()

        getComponent().inject(this)

        init.complete(true)
    }

    private fun startWebServer() {
        vertx
            .createHttpServer()
            .requestHandler(router)
            .listen(Integer.getInteger("http.port", PORT), System.getProperty("http.address", "0.0.0.0")) { result ->
                if (result.succeeded())
                    logger.info("Started listening port $PORT")
                else
                    logger.error("Failed to listen port $PORT")
            }
    }
}
package com.ahmetduruer.portfolio.di.component

import com.ahmetduruer.portfolio.Main
import com.ahmetduruer.portfolio.di.module.LoggerModule
import com.ahmetduruer.portfolio.di.module.RouterModule
import com.ahmetduruer.portfolio.di.module.VertxModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        (VertxModule::class),
        (LoggerModule::class),
        (RouterModule::class)
    ]
)
interface ApplicationComponent {
    fun inject(main: Main)
}
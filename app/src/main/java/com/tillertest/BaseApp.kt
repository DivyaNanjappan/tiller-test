package com.tillertest

import android.app.Application
import com.tillertest.di.component.AppComponent
import com.tillertest.di.component.DaggerAppComponent
import com.tillertest.di.component.DaggerNetComponent
import com.tillertest.di.component.NetComponent
import com.tillertest.di.module.AppModule
import com.tillertest.di.module.NetModule

@Suppress("DEPRECATION")
class BaseApp : Application() {
    lateinit var appComponent: AppComponent
    lateinit var netComponent: NetComponent

    override fun onCreate() {
        super.onCreate()

        initComponents()
    }

    private fun initComponents() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .netModule(NetModule(this))
            .build()

        netComponent = DaggerNetComponent.builder()
            .appModule(AppModule(this))
            .netModule(NetModule(this))
            .build()

        appComponent.inject(this)
    }
}
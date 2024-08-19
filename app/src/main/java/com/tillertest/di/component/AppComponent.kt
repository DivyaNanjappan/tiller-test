package com.tillertest.di.component

import com.tillertest.BaseApp
import com.tillertest.di.module.AppModule
import com.tillertest.utils.AppPreferenceManager
import com.tillertest.service.ConnectivityInterceptor
import com.tillertest.di.module.NetModule
import com.tillertest.viewmodel.AppViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (NetModule::class)])
interface AppComponent {

    fun inject(baseApp: BaseApp)

    fun inject(appPreferenceManager: AppPreferenceManager)

    fun inject(connectivityInterceptor: ConnectivityInterceptor)
}
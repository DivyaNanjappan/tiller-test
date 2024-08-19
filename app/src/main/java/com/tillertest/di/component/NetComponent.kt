package com.tillertest.di.component

import com.tillertest.di.module.AppModule
import com.tillertest.di.module.NetModule
import com.tillertest.viewmodel.AppViewModel
import com.tillertest.views.MainActivity
import com.tillertest.views.SplashActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (NetModule::class)])
interface NetComponent {

    /**
     * View Models
     */

    fun inject(viewModel: AppViewModel)

    /**
     * Activity/Fragments
     */
    fun inject(activity: SplashActivity)

    fun inject(activity: MainActivity)
}
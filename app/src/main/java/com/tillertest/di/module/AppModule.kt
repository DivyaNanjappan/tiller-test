package com.tillertest.di.module

import android.content.Context
import com.tillertest.BaseApp
import com.tillertest.repository.AppAuthRepository
import com.tillertest.repository.AppAuthRepositoryImpl
import com.tillertest.service.APIService
import com.tillertest.repository.AppRepository
import com.tillertest.repository.AppRepositoryImpl
import com.tillertest.service.APIAuthService
import com.tillertest.utils.AppPreferenceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private var baseApp: BaseApp) {

    @Provides
    @Singleton
    fun providesApplication(): BaseApp {
        return baseApp
    }

    @Provides
    @Singleton
    fun applicationContext(): Context {
        return baseApp.applicationContext
    }

    @Provides
    @Singleton
    fun provideAppPreferenceManager(): AppPreferenceManager {
        return AppPreferenceManager(baseApp)
    }

    @Provides
    fun provideAppRepository(dataSource: APIService): AppRepository {
        return AppRepositoryImpl(dataSource)
    }

    @Provides
    fun provideAppAuthRepository(dataSource: APIAuthService): AppAuthRepository {
        return AppAuthRepositoryImpl(dataSource)
    }
}
package com.tillertest.di.module

import com.google.android.datatransport.BuildConfig
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tillertest.BaseApp
import com.tillertest.service.APIAuthService
import com.tillertest.service.APIService
import com.tillertest.service.ConnectivityInterceptor
import com.tillertest.service.EndPoints
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

// Constructor needs one parameter to instantiate.
@Module
class NetModule(private var baseApp: BaseApp) {

    @Provides
    @Singleton
    internal fun provideOkHttpCache(baseApp: BaseApp): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        return Cache(baseApp.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
        gsonBuilder.setPrettyPrinting()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .serializeNulls()
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideConnectivityInterceptor(): ConnectivityInterceptor {
        return ConnectivityInterceptor(baseApp)
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(
        cache: Cache,
        connectivityInterceptor: ConnectivityInterceptor
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = okHttpLogLevel

        return OkHttpClient.Builder()
            .connectionSpecs(listOf(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT))
            .addInterceptor(loggingInterceptor)
            .addInterceptor(connectivityInterceptor)
            .cache(cache)
            .readTimeout(300, TimeUnit.SECONDS)
            .connectTimeout(300, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    internal fun provideAppAPI(gson: Gson, okHttpClient: OkHttpClient): APIService {
        val retrofit = Retrofit.Builder()
            .baseUrl(EndPoints.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
        return retrofit.create(APIService::class.java)
    }

    @Provides
    internal fun provideAppAuthAPI(gson: Gson, okHttpClient: OkHttpClient): APIAuthService {
        val retrofit = Retrofit.Builder()
            .baseUrl(EndPoints.BASE_URL_AUTH)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
        return retrofit.create(APIAuthService::class.java)
    }

    companion object {
        private val logLevel =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        private val okHttpLogLevel = logLevel
    }
}
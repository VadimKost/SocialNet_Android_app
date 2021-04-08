package com.v.vsocial.di

import com.v.vsocial.Api
import com.v.vsocial.api.auth.AuthInterceptor
import com.v.vsocial.utils.ResponseStateFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import java.util.concurrent.TimeUnit


@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        var httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level=HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttp (interceptor: HttpLoggingInterceptor,
                       authInterceptor: AuthInterceptor
    )
    : OkHttpClient {
        return OkHttpClient.Builder()
            .retryOnConnectionFailure(false)
            .connectTimeout(100, TimeUnit.MILLISECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("http://192.168.0.106:8000/api/")
            .addCallAdapterFactory(ResponseStateFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): Api =retrofit.create(Api::class.java)
}
//    private val BASE_URL = "http://www.vako.ga/api/"
//    private val  BASE_URL="http://192.168.0.106:8000/api/"
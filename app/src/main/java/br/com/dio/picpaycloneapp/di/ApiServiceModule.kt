package br.com.dio.picpaycloneapp.di

import br.com.dio.picpaycloneapp.domain.repositories.UserToken
import br.com.dio.picpaycloneapp.data.remote.services.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val API_URL = "http://192.168.0.103:8080"

@Module
@InstallIn(SingletonComponent::class)
class ApiServiceModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .client(createHttpClient())
        .baseUrl(API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private fun createHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addNetworkInterceptor(httpLoggingInterceptor)
            .addInterceptor(AuthorizationInterceptor())
            .connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS)
            .build()
    }

    inner class AuthorizationInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = if (UserToken.hasToken()) {
                chain.request().newBuilder()
                    .addHeader("Authorization", UserToken.bearerToken)
                    .build()
            } else chain.request()

            return chain.proceed(request)
        }

    }

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create<ApiService>()

}

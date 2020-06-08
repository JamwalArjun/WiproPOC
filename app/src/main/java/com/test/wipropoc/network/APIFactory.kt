package com.test.wipropoc.network


import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.test.wipropoc.R
import com.test.wipropoc.util.Logger
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Retrofit object for  api
 */
const val API_TIMEOUT: Long = 30

object APIFactory {

    fun getApi(): ServiceApi {
        val okHttpClient = makeOkHttpClient(
            makeLoggingInterceptor()
        )
        return makeApi(okHttpClient, makeGson())
    }

    private fun makeApi(okHttpClient: OkHttpClient, gson: Gson): ServiceApi {
        return makeRetrofit(okHttpClient, gson).create(ServiceApi::class.java)
    }

    private fun makeOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(API_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(API_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    private fun makeRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dl.dropboxusercontent.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private fun makeGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .create()
    }

    private fun makeLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor(httpLogger)
        loggingInterceptor.level =
            HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    private val httpLogger: HttpLoggingInterceptor.Logger by lazy {
        object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Logger.v(R.string.network_call, "$message")

            }
        }
    }
}
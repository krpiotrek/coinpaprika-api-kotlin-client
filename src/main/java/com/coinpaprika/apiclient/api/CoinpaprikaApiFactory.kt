/*
 * Created by Piotr Kostecki on 5/16/19 4:05 PM
 */

package com.coinpaprika.apiclient.api

import com.coinpaprika.apiclient.interceptors.CacheHeaderInterceptor
import com.coinpaprika.apiclient.repository.graphs.GraphsApiContract
import com.coinpaprika.apiclient.repository.oembed.CoinpaprikaOembedApiContract
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.ConnectionPool
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

open class CoinpaprikaApiFactory {
    companion object {
        private const val GRAPHS_URL = "https://graphs.coinpaprika.com/"
        private const val BASE_URL = "https://api.coinpaprika.com/v1/"
    }

    fun client(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(createClient().build())
            .build()
    }

    protected fun createClient(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .protocols(listOf(Protocol.HTTP_1_1, Protocol.HTTP_2))
            .writeTimeout(20000, TimeUnit.MILLISECONDS)
            .readTimeout(10000, TimeUnit.MILLISECONDS)
            .retryOnConnectionFailure(true)
            .connectTimeout(15000, TimeUnit.MILLISECONDS)
            .connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
    }

    fun graphs(): GraphsApiContract {
        return Retrofit.Builder()
            .baseUrl(GRAPHS_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(createClient()
                .addInterceptor(CacheHeaderInterceptor())
                .addInterceptor(createLoggingInterceptor())
                .build())
            .build()
            .create(GraphsApiContract::class.java)
    }

    fun oembed(baseUrl: String): CoinpaprikaOembedApiContract {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(createClient()
                .addInterceptor(createLoggingInterceptor())
                .build())
            .build()
            .create(CoinpaprikaOembedApiContract::class.java)
    }

    private fun createLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
}
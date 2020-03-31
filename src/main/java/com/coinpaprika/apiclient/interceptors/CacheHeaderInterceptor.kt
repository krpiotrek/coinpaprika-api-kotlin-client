/*
 * Created by Piotr Kostecki on 10/29/19 3:24 PM
 */

package com.coinpaprika.apiclient.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class CacheHeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        val request = request().newBuilder().header("Cache-Control", "public, max-age=" + 60 * 5).build()
        proceed(request)
    }
}
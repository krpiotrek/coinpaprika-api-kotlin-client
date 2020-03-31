/*
 * Created by Piotr Kostecki on 5/16/19 4:14 PM
 */

package com.coinpaprika.apiclient.repository.oembed

import com.coinpaprika.apiclient.entity.VideoEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinpaprikaOembedApiContract {
    @GET("oembed")
    suspend fun getYoutubeVideo(@Query("url") url: String,
                        @Query("format") format: String): VideoEntity

    @GET("oembed.json")
    suspend fun getVimeoVideo(@Path("url") url: String): VideoEntity
}
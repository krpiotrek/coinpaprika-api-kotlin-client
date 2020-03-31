/*
 * Created by Piotr Kostecki on 5/16/19 4:14 PM
 */

package com.coinpaprika.apiclient.repository.oembed

import com.coinpaprika.apiclient.api.CoinpaprikaApiFactory
import com.coinpaprika.apiclient.entity.VideoEntity

open class OembedApi constructor(
    private val baseUrl: String,
    private var retrofit: CoinpaprikaOembedApiContract = CoinpaprikaApiFactory().oembed(baseUrl)
) {

    suspend fun getYoutubeVideo(url: String, format: String): VideoEntity {
        return retrofit.getYoutubeVideo(url, format)
    }

    suspend fun getVimeoVideo(url: String): VideoEntity {
        return retrofit.getVimeoVideo(url)
    }
}
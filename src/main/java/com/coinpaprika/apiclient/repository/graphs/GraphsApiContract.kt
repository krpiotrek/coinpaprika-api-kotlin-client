/*
 * Created by Piotr Kostecki on 5/16/19 4:13 PM
 */

package com.coinpaprika.apiclient.repository.graphs

import com.coinpaprika.apiclient.entity.RawCoinGraphPointsEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface GraphsApiContract {
    @GET("currency/chart/{cryptocurrencyId}/{period}/chart.svg?mobile=1")
    suspend fun getChartSvg(
        @Path("cryptocurrencyId") cryptocurrencyId: String,
        @Path("period") period: String
    ): String

    @GET("currency/data/{cryptocurrencyId}/{period}/?quote=USD&mobile=1")
    suspend fun getCurrencyDataDefinedPeriod(
        @Path("cryptocurrencyId") cryptocurrencyId: String,
        @Path("period") period: String
    ): List<RawCoinGraphPointsEntity>

    @GET("currency/data/{cryptocurrencyId}/dates/{tsFrom}/{tsTo}/?quote=USD&mobile=1")
    suspend fun getCurrencyDataCustomPeriod(
        @Path("cryptocurrencyId") cryptocurrencyId: String,
        @Path("tsFrom") tsFrom: Long,
        @Path("tsTo") tsTo: Long
    ): List<RawCoinGraphPointsEntity>

    @GET("overview/total/{period}/?mobile=1")
    suspend fun getOverviewDataDefinedPeriod(
        @Path("period") period: String
    ): List<RawCoinGraphPointsEntity>
}
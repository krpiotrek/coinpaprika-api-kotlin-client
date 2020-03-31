/*
 * Created by Piotr Kostecki on 5/16/19 4:13 PM
 */

package com.coinpaprika.apiclient.repository.graphs

import com.coinpaprika.apiclient.api.CoinpaprikaApiFactory
import com.coinpaprika.apiclient.entity.RawCoinGraphPointsEntity

open class GraphsApi constructor(private var retrofit: GraphsApiContract = CoinpaprikaApiFactory().graphs()) {

    suspend fun chartSvg(cryptocurrencyId: String, period: String): String {
        return retrofit.getChartSvg(cryptocurrencyId, period)
    }

    suspend fun currencyDataDefinedPeriod(cryptocurrencyId: String, period: String): List<RawCoinGraphPointsEntity> {
        return retrofit.getCurrencyDataDefinedPeriod(cryptocurrencyId, period)
    }

    suspend fun currencyDataCustomPeriod(cryptocurrencyId: String, fromTs: Long, toTs: Long): List<RawCoinGraphPointsEntity> {
        return retrofit.getCurrencyDataCustomPeriod(cryptocurrencyId, fromTs, toTs)
    }

    suspend fun overviewDataDefinedPeriod(period: String): List<RawCoinGraphPointsEntity> {
        return retrofit.getOverviewDataDefinedPeriod(period)
    }
}
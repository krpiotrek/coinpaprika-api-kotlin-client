package com.coinpaprika.apiclient.repository.coin

import com.coinpaprika.apiclient.api.CoinpaprikaApiFactory
import com.coinpaprika.apiclient.entity.*
import com.coinpaprika.apiclient.extensions.safeApiCallRaw
import io.reactivex.Observable
import retrofit2.Response

class CoinApi constructor(
    private var retrofit: CoinApiContract = CoinpaprikaApiFactory()
        .client()
        .create(CoinApiContract::class.java)
) : CoinApiContract {

    override fun getCoin(id: String): Observable<Response<CoinDetailsEntity>> {
        return safeApiCallRaw { retrofit.getCoin(id) }
    }

    override fun getCoins(additionalFields: String?): Observable<Response<List<CoinEntity>>> {
        return safeApiCallRaw { retrofit.getCoins(additionalFields) }
    }

    override fun getEvents(id: String): Observable<Response<List<EventEntity>>> {
        return safeApiCallRaw { retrofit.getEvents(id) }
    }

    override fun getExchanges(id: String): Observable<Response<List<ExchangeEntity>>> {
        return safeApiCallRaw { retrofit.getExchanges(id) }
    }

    override fun getMarkets(id: String, quotes: String): Observable<Response<List<MarketEntity>>> {
        return safeApiCallRaw { retrofit.getMarkets(id, quotes) }
    }

    override fun getTweets(id: String): Observable<Response<List<TweetEntity>>> {
        return safeApiCallRaw { retrofit.getTweets(id) }
    }

    override fun addEvent(cryptoId: String, event: EventEntity): Observable<Response<Void>> {
        return safeApiCallRaw { retrofit.addEvent(cryptoId, event) }
    }
}
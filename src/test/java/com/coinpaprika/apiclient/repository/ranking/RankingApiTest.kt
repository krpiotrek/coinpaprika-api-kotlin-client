/*
 * Created by Piotr Kostecki on 09.01.19 16:45
 * kontakt@piotrkostecki.pl
 *
 * Last modified 09.01.19 16:45
 */

package com.coinpaprika.apiclient.repository.ranking

import android.content.Context
import com.coinpaprika.apiclient.entity.TopMoversEntity
import com.coinpaprika.apiclient.exception.NetworkConnectionException
import com.coinpaprika.apiclient.exception.ServerConnectionError
import com.coinpaprika.apiclient.exception.TooManyRequestsError
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class RankingApiTest {

    @Mock private lateinit var mockApi: RankingApiContract
    @Mock lateinit var mockMovers: TopMoversEntity
    @Mock private lateinit var mockContext: Context

    @Test
    fun `get movers happy case`() {
        val movers = mockMovers
        val response = Response.success(movers)

        `when`(mockApi.getTop10Movers())
            .thenReturn(Observable.just(response))

        val client = RankingApi(mockContext, mockApi)
        client.getTop10Movers()
            .map { it.body() }
            .test()
            .assertResult(movers)
            .assertComplete()
    }

    @Test
    fun `get movers too many requests error`() {
        val response = Response.error<TopMoversEntity>(429,
            ResponseBody.create(MediaType.parse("application/json"), "\"error\":\"too many requests\")"))

        `when`(mockApi.getTop10Movers())
            .thenReturn(Observable.just(response))

        val client = RankingApi(mockContext, mockApi)
        client.getTop10Movers()
            .test()
            .assertError(TooManyRequestsError::class.java)
            .assertNotComplete()
    }

    @Test
    fun `get movers server error`() {
        val response = Response.error<TopMoversEntity>(404,
            ResponseBody.create(MediaType.parse("text/html"), ""))

        `when`(mockApi.getTop10Movers())
            .thenReturn(Observable.just(response))

        val client = RankingApi(mockContext, mockApi)
        client.getTop10Movers()
            .test()
            .assertError(ServerConnectionError::class.java)
            .assertNotComplete()
    }

    @Test
    fun `get movers network connection error`() {
        given(mockApi.getTop10Movers())
            .willAnswer {
                throw NetworkConnectionException()
            }

        val client = RankingApi(mockContext, mockApi)
        client.getTop10Movers()
            .test()
            .assertError(NetworkConnectionException::class.java)
            .assertNotComplete()
    }
}
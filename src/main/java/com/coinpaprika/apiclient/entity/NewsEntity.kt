/*
 * Created by Piotr Kostecki on 27.12.18 18:43
 * kontakt@piotrkostecki.pl
 *
 * Last modified 27.12.18 18:43
 */

package com.coinpaprika.apiclient.entity

import com.google.gson.annotations.SerializedName

data class NewsEntity(val title: String,
                      val url: String,
                      @SerializedName("news_date") val newsDate: String)
package com.easyinvest.core

import com.easyinvest.data.PortfolioDto
import com.easyinvest.data.TraderDto
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

val CURRENT_USER_ID = "1"

interface EthfinexApi {

    @GET("/user/{id}")
    fun portfolio(@Path("id") userId: String = CURRENT_USER_ID): Single<PortfolioDto>

    @GET("/traders")
    fun traders(): Single<List<TraderDto>>

    @FormUrlEncoded
    @POST("/subscription/create/")
    fun follow(
        @Field("follower") investorId: String = CURRENT_USER_ID,
        @Field("user_followed") traderId: String,
        @Field("money_allocated") moneyAllocated: Float
    ): Completable

    @POST("/subscription/delete/{id}")
    fun unfollow(@Path("id") subscriptionId: String): Completable

}
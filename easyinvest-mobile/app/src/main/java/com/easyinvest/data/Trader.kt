package com.easyinvest.data

import java.io.Serializable

data class Trader(
    val id: String,
    val name: String,
    val avatar: String,
    val profitPercentage: Int,
    val followersCount: Int,
    val followedByCurrentInvestor: Boolean,
    val pricePerMonth: Float,
    val subscriptionId: String? = null

    //TODO data for graphic
) : Serializable
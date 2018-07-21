package com.easyinvest.data

import java.io.Serializable

data class Trader(
    val name: String,
    val avatar: String,
    val profitPercentage: Int,
    val followersCount: Int,
    val followedByCurrentInvestor: Boolean,
    val pricePerMonth: Float

    //TODO data for graphic
) : Serializable
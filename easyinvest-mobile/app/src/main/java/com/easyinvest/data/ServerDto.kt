package com.easyinvest.data

import java.util.*

data class PortfolioDto(
    val totalMoney: Float,
    val startMoney: Float,
    val freeMoney: Float,

    val subscriptionDto: List<SubscriptionDto>
)

data class SubscriptionDto(
    val userFollowedId: String,
    val moneyAllocated: Float,
    val totalMoney: Float,
    val trader: TraderDto
)

data class TraderDto(
    val id: String,
    val username: String,
    val isFollowed: Boolean,
    val monthGrowth: Float,
    val followersCount: Int,
    val dataBalances: List<DataBalanceDto>
)

data class DataBalanceDto(
    val date: Date,
    val balance: Float
)
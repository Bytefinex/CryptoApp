package com.easyinvest.portfolio.items

import com.easyinvest.base.DisplayableItem
import com.easyinvest.data.Trader
import com.easyinvest.traders.PopularTraderItem

data class TraderItem(
        val id: String,
        val name: String,
        val totalAmount: Float,
        val extraAmount: Float?,
        val forcedAvatar: String? = null
) : DisplayableItem {

    private val avatarSource = "https://randomuser.me/api/portraits/men/%d.jpg"
    private val numberOfPhotos = 94
    private val displayMoneyPattern = "\$%.2f"

    val avatar = forcedAvatar ?: avatarSource.format(id.hashCode() % numberOfPhotos)

    val displayTotalAmount = displayMoneyPattern.format(totalAmount)
    val displayExtraAmount = displayMoneyPattern.format(extraAmount)

    fun toTrader(): Trader {
        val percentage = ((totalAmount + (extraAmount ?: 0f)) / totalAmount - totalAmount).toInt()
        return Trader(
                name = name,
                avatar = avatar,
                profitPercentage = percentage,
                pricePerMonth = 10f,
                followersCount = 1334,
                followedByCurrentInvestor = true
        )
    }

    fun toPopular(): PopularTraderItem {
        return PopularTraderItem(this)
    }
}
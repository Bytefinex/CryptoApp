package com.easyinvest.traders

import com.easyinvest.base.DisplayableItem
import com.easyinvest.data.Trader
import com.easyinvest.portfolio.items.TraderItem

data class PopularTraderItem(val item: TraderItem, val monthlyPercent: Int) : DisplayableItem {
    fun toTrader(): Trader {
        return Trader(
                id = item.id,
                name = item.name,
                avatar = item.avatar,
                profitPercentage = monthlyPercent,
                pricePerMonth = 10f,
                followersCount = 334,
                followedByCurrentInvestor = true
        )
    }
}
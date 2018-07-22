package com.easyinvest.traders

import com.easyinvest.base.DisplayableItem
import com.easyinvest.portfolio.items.TraderItem

data class PopularTraderItem(val item: TraderItem, val monthlyPercent: Int) : DisplayableItem
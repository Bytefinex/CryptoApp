package com.easyinvest.portfolio.items

import com.easyinvest.base.DisplayableItem

data class HeaderItem(
        val totalAmount: String,
        val extraAmount: String,
        val extraAmountValue : Float
) : DisplayableItem
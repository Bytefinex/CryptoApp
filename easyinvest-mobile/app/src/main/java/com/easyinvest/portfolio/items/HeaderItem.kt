package com.easyinvest.portfolio.items

import com.easyinvest.base.DisplayableItem
import com.easyinvest.util.toPercent

data class HeaderItem(
        val totalAmount: Float,
        val extraAmount: Float
) : DisplayableItem {
    private val patternTotal = "\$%.2f"
    private val patternExtra = "\$%.2f (%d%%)"


    val displayTotalAmount = patternTotal.format(totalAmount)
    val displayExtraAmount = patternExtra.format(extraAmount, toPercent(totalMoney = totalAmount, extraMoney = extraAmount))
}
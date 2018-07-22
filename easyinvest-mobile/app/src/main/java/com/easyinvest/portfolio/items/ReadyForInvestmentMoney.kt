package com.easyinvest.portfolio.items

import com.easyinvest.base.DisplayableItem

data class ReadyForInvestmentMoney(
        val totalAmount: Float
) : DisplayableItem {
    val name: String = "USD"
    val avatar = "https://trigjig.com/wp-content/uploads/us-01.png"
    val displayTotalAmount = "$%.2f".format(totalAmount)
}
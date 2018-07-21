package com.easyinvest.portfolio.items

data class TraderItem(
        val id: String,
        val name: String,
        val totalAmount: Float,
        val extraAmount: Float?,
        val forcedAvatar: String? = null
) : PortfolioDisplayableItem {

    private val avatarSource = "https://randomuser.me/api/portraits/men/%d.jpg"
    private val numberOfPhotos = 94
    private val displayMoneyPattern = "\$%.2f"

    val avatar = forcedAvatar ?: avatarSource.format(id.hashCode() % numberOfPhotos)

    val displayTotalAmount = displayMoneyPattern.format(totalAmount)
    val displayExtraAmount = displayMoneyPattern.format(extraAmount)
}
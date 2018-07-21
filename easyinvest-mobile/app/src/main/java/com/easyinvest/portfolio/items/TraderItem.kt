package com.easyinvest.portfolio.items

data class TraderItem(
        val id: String,
        val name: String,
        val totalAmount: String,
        val extraAmount: String?,
        val forcedAvatar: String? = null
) : PortfolioDisplayableItem {

    private val avatarSource = "https://randomuser.me/api/portraits/men/%d.jpg"
    private val numberOfPhotos = 94

    val avatar = forcedAvatar ?: avatarSource.format(id.hashCode() % numberOfPhotos)
}
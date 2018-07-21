package com.easyinvest.core

import com.easyinvest.base.DisplayableItem
import com.easyinvest.portfolio.items.HeaderItem
import com.easyinvest.portfolio.items.SectionHeaderItem
import com.easyinvest.portfolio.items.TraderItem
import com.easyinvest.traders.PopularTraderItem
import io.reactivex.Single

object MainDataSource {
    fun getPortfolio(): Single<List<DisplayableItem>> =
            Single.just(
                    listOf(
                            HeaderItem(totalAmount = "\$10 376,76", extraAmount = "\$1 500,61"),
                            SectionHeaderItem(title = "Following"),
                            TraderItem(id = "1", name = "John Doe", totalAmount = 1088.97f, extraAmount = -54.16f),
                            TraderItem(id = "2", name = "Apple Seed", totalAmount = 1031.86f, extraAmount = 15.32f),
                            TraderItem(id = "3", name = "Vitalik Buterin", totalAmount = 1305.96f, extraAmount = 304.83f),
                            TraderItem(id = "4", name = "Satoshi Nakamoto", totalAmount = 3088.96f, extraAmount = 808.14f),
                            SectionHeaderItem(title = "Ready for investments"),
//                TraderItem(id = "5", name = "Ethereum", totalAmount = "10,77", extraAmount = "1,14", forcedAvatar = "https://ih1.redbubble.net/image.358612536.1165/flat,550x550,075,f.jpg"),
                            TraderItem(id = "6", name = "USD", totalAmount = 2000.77f, extraAmount = null, forcedAvatar = "https://trigjig.com/wp-content/uploads/us-01.png")
                    )
            )


    fun getPopularTraders(): Single<List<PopularTraderItem>> =
            getPortfolio()
                    .map {
                        it
                                .filter { it is TraderItem }
                                .map { it as TraderItem }
                                .map { PopularTraderItem(it) }
                    }
}
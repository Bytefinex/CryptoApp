package com.easyinvest.core

import com.easyinvest.base.DisplayableItem
import com.easyinvest.data.PortfolioDto
import com.easyinvest.portfolio.items.HeaderItem
import com.easyinvest.portfolio.items.SectionHeaderItem
import com.easyinvest.portfolio.items.TraderItem
import com.easyinvest.traders.PopularTraderItem
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import kotlin.math.roundToInt

object Feature {
    private val subject = BehaviorSubject.create<PortfolioDto>()

    private var refreshDisposable: Disposable? = null

    fun refresh() {
        if (refreshDisposable?.isDisposed != false) {
            refreshDisposable = RetrofitService.api.portfolio()
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess { subject.onNext(it) }
                    .subscribe()
        }
    }

    fun getPortfolio(): Single<List<DisplayableItem>> =
            RetrofitService.api.portfolio()
                    .map {
                        portfolioHeader(it) + (it.subscription.map {
                            TraderItem(
                                    id = it.userFollowedId,
                                    name = it.trader.username,
                                    totalAmount = it.totalMoney,
                                    extraAmount = it.totalMoney - it.moneyAllocated
                            )
                        } ?: emptyList())
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorReturn { mockedList(PortfolioDto(5000f, 4000f, 500f, emptyList())) }

    private fun mockedList(portfolioDto: PortfolioDto): List<DisplayableItem> {
        return portfolioHeader(portfolioDto) + listOf<DisplayableItem>(
                TraderItem(
                        id = "1",
                        name = "John Doe",
                        totalAmount = 1088.97f,
                        extraAmount = -54.16f
                ),
                TraderItem(
                        id = "2",
                        name = "Apple Seed",
                        totalAmount = 1031.86f,
                        extraAmount = 15.32f
                ),
                TraderItem(
                        id = "3",
                        name = "Vitalik Buterin",
                        totalAmount = 1305.96f,
                        extraAmount = 304.83f
                ),
                TraderItem(
                        id = "4",
                        name = "Satoshi Nakamoto",
                        totalAmount = 3088.96f,
                        extraAmount = 808.14f
                )
        )
    }

    private fun portfolioHeader(portfolioDto: PortfolioDto): List<DisplayableItem> {
        return listOf(
                HeaderItem(
                        totalAmount = "\$${portfolioDto.totalMoney}",
                        extraAmount = "\$${portfolioDto.totalMoney - portfolioDto.startMoney}"
                ),
                SectionHeaderItem(title = "Ready for investments"),
                //                TraderItem(id = "5", name = "Ethereum", totalAmount = "10,77", extraAmount = "1,14", forcedAvatar = "https://ih1.redbubble.net/image.358612536.1165/flat,550x550,075,f.jpg"),
                TraderItem(
                        id = "6",
                        name = "USD",
                        totalAmount = portfolioDto.freeMoney,
                        extraAmount = null,
                        forcedAvatar = "https://trigjig.com/wp-content/uploads/us-01.png"
                ),
                SectionHeaderItem(title = "Following")
        )
    }


    fun getPopularTraders(): Single<List<PopularTraderItem>> =
            getPortfolio()
                    .map {
                        it
                                .filter { it is TraderItem }
                                .map { it as TraderItem }
                                .map { PopularTraderItem(it) }
                    }
                    .observeOn(AndroidSchedulers.mainThread())

    // just round this fckng number
    fun availableMoneyToInvest(): Observable<Int> =
            subject.map { it.freeMoney.roundToInt() }
                    .observeOn(AndroidSchedulers.mainThread())

    fun follow(traderId: String, amount: Float) {
        RetrofitService.api.follow(traderId = traderId, moneyAllocated = amount)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { refresh() }
                .subscribe()
    }

    fun followState(traiderId: String): Observable<Boolean> =
            subject.map { it.subscription.any { it.trader.id == traiderId } }
                    .observeOn(AndroidSchedulers.mainThread())

    fun unfollow(traderId: String) {
        RetrofitService.api.unfollow(traderId = traderId)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { refresh() }
                .subscribe()
    }

}
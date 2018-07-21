package com.easyinvest.portfolio

import android.app.Activity
import com.easyinvest.portfolio.delegates.HeaderDelegate
import com.easyinvest.portfolio.delegates.SectionHeaderDelegate
import com.easyinvest.portfolio.delegates.TraderDelegate
import com.easyinvest.portfolio.items.PortfolioDisplayableItem
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter

class PortfolioAdapter constructor(activity: Activity, items: List<PortfolioDisplayableItem>) : ListDelegationAdapter<List<PortfolioDisplayableItem>>() {

    init {
        delegatesManager.addDelegate(HeaderDelegate(activity))
        delegatesManager.addDelegate(TraderDelegate(activity))
        delegatesManager.addDelegate(SectionHeaderDelegate(activity))
        setItems(items)
    }
}
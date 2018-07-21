package com.easyinvest.portfolio

import android.app.Activity
import com.easyinvest.portfolio.delegates.PortfolioHeaderDelegate
import com.easyinvest.portfolio.items.PortfolioDisplayableItem
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter

class PortfolioAdapter constructor(activity: Activity, items: List<PortfolioDisplayableItem>) : ListDelegationAdapter<List<PortfolioDisplayableItem>>() {

    init {
        delegatesManager.addDelegate(PortfolioHeaderDelegate(activity))
        setItems(items)
    }
}
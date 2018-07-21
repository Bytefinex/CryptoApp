package com.easyinvest.portfolio

import android.app.Activity
import com.easyinvest.base.DisplayableItem
import com.easyinvest.portfolio.delegates.HeaderDelegate
import com.easyinvest.portfolio.delegates.SectionHeaderDelegate
import com.easyinvest.portfolio.delegates.TraderDelegate
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter

class PortfolioAdapter constructor(activity: Activity, items: List<DisplayableItem>) : ListDelegationAdapter<List<DisplayableItem>>() {

    init {
        delegatesManager.addDelegate(HeaderDelegate(activity))
        delegatesManager.addDelegate(TraderDelegate(activity, items))
        delegatesManager.addDelegate(SectionHeaderDelegate(activity))
        setItems(items)
    }
}
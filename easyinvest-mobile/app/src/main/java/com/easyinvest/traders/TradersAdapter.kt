package com.easyinvest.traders

import android.app.Activity
import com.easyinvest.base.DisplayableItem
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter

class TradersAdapter (activity: Activity, items: List<DisplayableItem>) : ListDelegationAdapter<List<DisplayableItem>>() {
    init {
        delegatesManager.addDelegate(PopularTradersDelegate(activity, items))
        setItems(items)
    }
}
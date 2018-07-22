package com.easyinvest.portfolio.delegates

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.easyinvest.R
import com.easyinvest.base.DisplayableItem
import com.easyinvest.portfolio.items.HeaderItem
import com.easyinvest.util.colorifyProfit
import com.hannesdorfmann.adapterdelegates3.AbsListItemAdapterDelegate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_portfolio_header.*

class HeaderDelegate(activity: Activity) : AbsListItemAdapterDelegate<HeaderItem, DisplayableItem, HeaderDelegate.HeaderViewHolder>() {
    private val inflater = activity.layoutInflater

    override fun onCreateViewHolder(parent: ViewGroup) =
            HeaderViewHolder(inflater.inflate(R.layout.item_portfolio_header, parent, false))

    override fun isForViewType(item: DisplayableItem, items: MutableList<DisplayableItem>, position: Int) =
            item is HeaderItem

    override fun onBindViewHolder(item: HeaderItem, viewHolder: HeaderViewHolder, payloads: MutableList<Any>) =
            viewHolder.bind(item)

    class HeaderViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(item: HeaderItem) {
            totalAmountView.text = item.displayTotalAmount
            colorifyProfit(extraAmountView, extraAmountImage, isPositive = item.extraAmount >= 0, displayText = item.displayExtraAmount)
        }
    }

}
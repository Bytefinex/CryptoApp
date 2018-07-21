package com.easyinvest.portfolio.delegates

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.easyinvest.R
import com.easyinvest.portfolio.items.HeaderItem
import com.easyinvest.portfolio.items.PortfolioDisplayableItem
import com.hannesdorfmann.adapterdelegates3.AbsListItemAdapterDelegate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_porfolio_header.*

class PortfolioHeaderDelegate(activity: Activity) : AbsListItemAdapterDelegate<HeaderItem, PortfolioDisplayableItem, PortfolioHeaderDelegate.HeaderViewHolder>() {
    private val inflater = activity.layoutInflater

    override fun onCreateViewHolder(parent: ViewGroup) =
            HeaderViewHolder(inflater.inflate(R.layout.item_porfolio_header, parent, false))

    override fun isForViewType(item: PortfolioDisplayableItem, items: MutableList<PortfolioDisplayableItem>, position: Int) =
            item is HeaderItem

    override fun onBindViewHolder(item: HeaderItem, viewHolder: HeaderViewHolder, payloads: MutableList<Any>) =
            viewHolder.bind(item)

    class HeaderViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(item: HeaderItem) {
            totalAmountView.text = item.totalAmount
            extraAmountView.text = item.extraAmount
        }
    }

}
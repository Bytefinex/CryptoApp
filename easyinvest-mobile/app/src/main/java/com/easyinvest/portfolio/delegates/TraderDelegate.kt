package com.easyinvest.portfolio.delegates

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.easyinvest.R
import com.easyinvest.portfolio.items.PortfolioDisplayableItem
import com.easyinvest.portfolio.items.TraderItem
import com.hannesdorfmann.adapterdelegates3.AbsListItemAdapterDelegate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_portfolio_trader.*

class TraderDelegate(activity: Activity) : AbsListItemAdapterDelegate<TraderItem, PortfolioDisplayableItem, TraderDelegate.TraderViewHolder>() {
    private val inflater = activity.layoutInflater

    override fun onCreateViewHolder(parent: ViewGroup) =
            TraderViewHolder(inflater.inflate(R.layout.item_portfolio_trader, parent, false))

    override fun isForViewType(item: PortfolioDisplayableItem, items: MutableList<PortfolioDisplayableItem>, position: Int) =
            item is TraderItem

    override fun onBindViewHolder(item: TraderItem, viewHolder: TraderViewHolder, payloads: MutableList<Any>) =
            viewHolder.bind(item)

    class TraderViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(item: TraderItem) {
            traderTotalAmountView.text = item.totalAmount
            traderNameView.text = item.name
            traderExtraAmountView.text = item.extraAmount
            Glide.with(itemView)
                    .load(item.avatar)
                    .apply(RequestOptions.circleCropTransform())
                    .into(traderImageView)
            traderExtraAmountView.visibility = if (item.extraAmount == null) View.GONE else View.VISIBLE
        }
    }

}
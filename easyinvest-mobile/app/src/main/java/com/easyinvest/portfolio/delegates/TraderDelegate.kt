package com.easyinvest.portfolio.delegates

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.easyinvest.R
import com.easyinvest.base.DisplayableItem
import com.easyinvest.portfolio.items.TraderItem
import com.easyinvest.ui.TraderDetailsActivity
import com.easyinvest.util.colorifyProfit
import com.hannesdorfmann.adapterdelegates3.AbsListItemAdapterDelegate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_portfolio_trader.*

class TraderDelegate(
        private val activity: Activity,
        private val items: List<DisplayableItem>
) : AbsListItemAdapterDelegate<TraderItem, DisplayableItem, TraderDelegate.TraderViewHolder>() {

    private val inflater = activity.layoutInflater

    override fun onCreateViewHolder(parent: ViewGroup) =
            TraderViewHolder(inflater.inflate(R.layout.item_portfolio_trader, parent, false))

    override fun isForViewType(item: DisplayableItem, items: MutableList<DisplayableItem>, position: Int) =
            item is TraderItem

    override fun onBindViewHolder(item: TraderItem, viewHolder: TraderViewHolder, payloads: MutableList<Any>) =
            viewHolder.bind(item)

    private fun onClickTrader(item: TraderItem) {
        if (item.extraAmount == null) {
            // Click on not real trader (USD)
            return
        }
        activity.startActivity(TraderDetailsActivity.getIntent(activity, item.toTrader()))
    }

    inner class TraderViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            containerView?.setOnClickListener {
                onClickTrader(items[adapterPosition] as TraderItem)
            }
        }

        fun bind(item: TraderItem) {
            traderNameView.text = item.name
            traderTotalAmountView.text = item.displayTotalAmount
            traderExtraAmountView.text = item.displayExtraAmount
            Glide.with(itemView)
                    .load(item.avatar)
                    .apply(RequestOptions.circleCropTransform())
                    .into(traderImageView)
            colorifyProfit(traderExtraAmountView, traderExtraAmountImage, (item.extraAmount
                    ?: 0f) >= 0, item.displayExtraAmount)

            val isTrader = item.extraAmount != null
            containerView?.isClickable = isTrader
            traderExtraAmountView.visibility = if (isTrader) View.VISIBLE else View.GONE
            traderExtraAmountImage.visibility = if (isTrader) View.VISIBLE else View.GONE
        }
    }

}
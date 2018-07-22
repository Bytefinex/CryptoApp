package com.easyinvest.portfolio.delegates

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.easyinvest.OpenTraders
import com.easyinvest.R
import com.easyinvest.base.DisplayableItem
import com.easyinvest.portfolio.items.ReadyForInvestmentMoney
import com.hannesdorfmann.adapterdelegates3.AbsListItemAdapterDelegate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_portfolio_ready.*

class ReadyForInvestmentDelegate(
        private val activity: Activity,
        private val opener: OpenTraders
) : AbsListItemAdapterDelegate<ReadyForInvestmentMoney, DisplayableItem, ReadyForInvestmentDelegate.ReadyForInvestmentMoneyViewHolder>() {

    private val inflater = activity.layoutInflater

    override fun onCreateViewHolder(parent: ViewGroup) =
            ReadyForInvestmentMoneyViewHolder(inflater.inflate(R.layout.item_portfolio_ready, parent, false))

    override fun isForViewType(item: DisplayableItem, items: MutableList<DisplayableItem>, position: Int) =
            item is ReadyForInvestmentMoney

    override fun onBindViewHolder(item: ReadyForInvestmentMoney, viewHolder: ReadyForInvestmentMoneyViewHolder, payloads: MutableList<Any>) =
            viewHolder.bind(item)

    inner class ReadyForInvestmentMoneyViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(item: ReadyForInvestmentMoney) {
            traderNameView.text = item.name
            traderTotalAmountView.text = item.displayTotalAmount
            Glide.with(itemView)
                    .load(item.avatar)
                    .apply(RequestOptions.circleCropTransform())
                    .into(traderImageView)
            investButton.setOnClickListener {
                opener.openTraders()
            }
        }
    }

}
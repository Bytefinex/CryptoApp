package com.easyinvest.portfolio.delegates

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.easyinvest.R
import com.easyinvest.base.DisplayableItem
import com.easyinvest.portfolio.items.SectionHeaderItem
import com.hannesdorfmann.adapterdelegates3.AbsListItemAdapterDelegate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_portfolio_section_header.*

class SectionHeaderDelegate(activity: Activity) : AbsListItemAdapterDelegate<SectionHeaderItem, DisplayableItem, SectionHeaderDelegate.SectionHeaderViewHolder>() {
    private val inflater = activity.layoutInflater

    override fun onCreateViewHolder(parent: ViewGroup) =
            SectionHeaderViewHolder(inflater.inflate(R.layout.item_portfolio_section_header, parent, false))

    override fun isForViewType(item: DisplayableItem, items: MutableList<DisplayableItem>, position: Int) =
            item is SectionHeaderItem

    override fun onBindViewHolder(item: SectionHeaderItem, viewHolder: SectionHeaderViewHolder, payloads: MutableList<Any>) =
            viewHolder.bind(item)

    class SectionHeaderViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(item: SectionHeaderItem) {
            sectionHeaderTitleView.text = item.title
        }
    }

}
package com.easyinvest.portfolio

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.easyinvest.R
import com.easyinvest.base.BaseFragment
import com.easyinvest.portfolio.items.HeaderItem
import com.easyinvest.portfolio.items.PortfolioDisplayableItem
import com.easyinvest.portfolio.items.SectionHeaderItem
import kotlinx.android.synthetic.main.fragment_portfolio.*


class PortfolioFragment : BaseFragment() {

    companion object {
        fun instance() = PortfolioFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_portfolio, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        portfolioRecyclerView.adapter = PortfolioAdapter(activity!!, getViewItems())
        portfolioRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun getViewItems(): List<PortfolioDisplayableItem> {
        return listOf(
                HeaderItem(totalAmount = "388 414,87", extraAmount = "46 888, 28"),
                SectionHeaderItem(title = "Following")
        )
    }


}
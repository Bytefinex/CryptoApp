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
import com.easyinvest.portfolio.items.TraderItem
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
                HeaderItem(totalAmount = "\$10 376,76", extraAmount = "\$1 500,61"),
                SectionHeaderItem(title = "Following"),
                TraderItem(id = "1", name = "John Doe", totalAmount = "\$1088,97", extraAmount = "\$54,16"),
                TraderItem(id = "2", name = "Apple Seed", totalAmount = "\$1031,86", extraAmount = "\$15,32"),
                TraderItem(id = "3", name = "Vitalik Buterin", totalAmount = "\$1305,96", extraAmount = "\$304,83"),
                TraderItem(id = "4", name = "Satoshi Nakamoto", totalAmount = "\$3088,96", extraAmount = "\$808,14"),
                SectionHeaderItem(title = "Ready for investments"),
//                TraderItem(id = "5", name = "Ethereum", totalAmount = "10,77", extraAmount = "1,14", forcedAvatar = "https://ih1.redbubble.net/image.358612536.1165/flat,550x550,075,f.jpg"),
                TraderItem(id = "6", name = "USD", totalAmount = "\$2000,77", extraAmount = null, forcedAvatar = "https://trigjig.com/wp-content/uploads/us-01.png")
        )
    }

}
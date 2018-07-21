package com.easyinvest.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.easyinvest.R
import com.easyinvest.data.Trader
import com.easyinvest.ui.TraderDetailsActivity
import kotlinx.android.synthetic.main.fragment_traders.*

class TradersFragment : Fragment() {

    companion object {
        fun instance() = TradersFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        inflater.inflate(R.layout.fragment_traders, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        openTrader.setOnClickListener {
            activity?.let {
                startActivity(
                    TraderDetailsActivity.getIntent(
                        it,
                        Trader(
                            "Michael Jordan",
                            "https://wallpaperbrowse.com/media/images/5725739-random-picture.jpg",
                            123,
                            300,
                            false,
                            10f
                        )
                    )
                )
            }
        }
    }
}
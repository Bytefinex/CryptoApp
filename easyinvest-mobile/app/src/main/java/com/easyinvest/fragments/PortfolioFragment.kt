package com.easyinvest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.easyinvest.R
import com.easyinvest.base.BaseFragment


class PortfolioFragment : BaseFragment() {

    companion object {
        fun instance() = PortfolioFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.fragment_portfolio, container, false)
}
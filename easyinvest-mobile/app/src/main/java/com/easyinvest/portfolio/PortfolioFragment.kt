package com.easyinvest.portfolio

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.easyinvest.R
import com.easyinvest.base.BaseFragment
import com.easyinvest.core.Feature
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_portfolio.*


class PortfolioFragment : BaseFragment() {

    companion object {
        fun instance() = PortfolioFragment()
    }

    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_portfolio, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        portfolioRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onStart() {
        super.onStart()
        compositeDisposable.add(Feature.getPortfolio().subscribe({ items ->
            activity?.let {
                portfolioRecyclerView.adapter = PortfolioAdapter(it, items)
            }
        }, {
            showNoInternetToast()
        }))
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }
}
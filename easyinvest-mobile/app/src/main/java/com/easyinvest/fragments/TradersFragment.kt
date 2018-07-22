package com.easyinvest.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.easyinvest.R
import com.easyinvest.base.BaseFragment
import com.easyinvest.core.Feature
import com.easyinvest.traders.TradersAdapter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_traders.*

class TradersFragment : BaseFragment() {

    companion object {
        fun instance() = TradersFragment()
    }

    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ) =
            inflater.inflate(R.layout.fragment_traders, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tradersRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onStart() {
        super.onStart()

        compositeDisposable.add(
                Feature.getPopularTraders().subscribe({ item ->
                    this@TradersFragment.activity?.let {
                        tradersRecyclerView.adapter = TradersAdapter(it, item)
                    }
                }, {
                    showNoInternetToast()
                })
        )
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }
}
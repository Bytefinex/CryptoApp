package com.easyinvest.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.easyinvest.R
import com.easyinvest.data.Trader
import kotlinx.android.synthetic.main.activity_trader_details.*
import kotlinx.android.synthetic.main.trader_header.*

private const val TRADER_KEY = "TRADER_KEY"

class TraderDetailsActivity : AppCompatActivity() {

    companion object {
        fun getIntent(context: Context, trader: Trader) =
            Intent(context, TraderDetailsActivity::class.java).apply {
                putExtra(TRADER_KEY, trader)
            }

        private fun fromIntent(intent: Intent) = intent.extras[TRADER_KEY] as Trader
    }

    private val trader by lazy { fromIntent(intent) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trader_details)
        setSupportActionBar(toolbar)

        toolbar_layout.title = trader.name

        Glide.with(this)
            .load(trader.avatar)
            .apply(RequestOptions.circleCropTransform()).into(avatar)

        updateSubscribeIcon(trader.followedByCurrentInvestor)

        fab.setOnClickListener { view ->
            Snackbar.make(
                view,
                "You have successfully ${if (trader.followedByCurrentInvestor) "subscribed" else "unsubscribed"}!",
                Snackbar.LENGTH_LONG
            ).show()

            updateSubscribeIcon(!trader.followedByCurrentInvestor)
        }
    }

    private fun updateSubscribeIcon(followedByCurrentInvestor: Boolean) {
        fab.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                if (followedByCurrentInvestor) R.drawable.ic_unsubscribe else R.drawable.ic_subscribe
            )
        )
    }
}

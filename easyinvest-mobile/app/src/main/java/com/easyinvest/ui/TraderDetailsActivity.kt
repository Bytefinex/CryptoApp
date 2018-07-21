package com.easyinvest.ui

import android.animation.PropertyValuesHolder
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.BounceInterpolator
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.db.chart.animation.Animation
import com.db.chart.model.LineSet
import com.db.chart.renderer.AxisRenderer
import com.db.chart.tooltip.Tooltip
import com.db.chart.util.Tools
import com.easyinvest.R
import com.easyinvest.data.Trader
import kotlinx.android.synthetic.main.trader_header.*
import kotlin.math.absoluteValue


private const val TRADER_KEY = "TRADER_KEY"

fun TextView.setTextViewDrawableColor(color: Int) {
    for (drawable in compoundDrawables) {
        if (drawable != null) {
            drawable.colorFilter =
                    PorterDuffColorFilter(
                            ContextCompat.getColor(context, color),
                            PorterDuff.Mode.SRC_IN
                    )
        }
    }
}

class TraderDetailsActivity : AppCompatActivity() {

    companion object {
        fun getIntent(context: Context, trader: Trader) =
                Intent(context, TraderDetailsActivity::class.java).apply {
                    putExtra(TRADER_KEY, trader)
                }

        private fun fromIntent(intent: Intent) = intent.extras[TRADER_KEY] as Trader
    }

    private val trader by lazy { fromIntent(intent) }

    private val labels = arrayOf("Jan", "Fev", "Mar", "Apr", "Jun", "May", "Jul", "Aug", "Sep")

    private val values = listOf(5, 6, 4, 8, 6, 9, 11, 13, 7).map { it.toFloat() }.toFloatArray()

    private lateinit var ttooltip: Tooltip

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trader_details)

        name.text = trader.name

        Glide.with(this)
                .load(trader.avatar)
                .apply(RequestOptions.circleCropTransform())
                .into(avatar)

        val colorAndIcon = if (trader.profitPercentage >= 0) {
            R.color.green to R.drawable.ic_trending_up
        } else R.color.red to R.drawable.ic_trending_down

        with(profit) {
            setTextColor(ContextCompat.getColor(this@TraderDetailsActivity, colorAndIcon.first))
            setCompoundDrawablesWithIntrinsicBounds(
                    colorAndIcon.second,
                    0,
                    0,
                    0
            )
            setTextViewDrawableColor(colorAndIcon.first)
            text = "${trader.profitPercentage.absoluteValue}%"
        }

        profitPerMonth.visibility = if (trader.followedByCurrentInvestor) {
            View.GONE
        } else {
            View.VISIBLE
        }

        if (trader.followersCount == 0) {
            followers.text = "has no followers"
        } else {
            followers.text = "${trader.followersCount} followers"
        }

        updateSubscribeIcon(trader.followedByCurrentInvestor)

        // Tooltip
        ttooltip = Tooltip(this, R.layout.linechart_three_tooltip, R.id.value)

        ttooltip.setVerticalAlignment(Tooltip.Alignment.BOTTOM_TOP)
        ttooltip.setDimensions(Tools.fromDpToPx(58f).toInt(), Tools.fromDpToPx(25f).toInt())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {

            ttooltip.setEnterAnimation(
                    PropertyValuesHolder.ofFloat(View.ALPHA, 1f),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f),
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 1f)
            ).duration = 200

            ttooltip.setExitAnimation(
                    PropertyValuesHolder.ofFloat(View.ALPHA, 0f),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f),
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 0f)
            ).duration = 200

            ttooltip.pivotX = Tools.fromDpToPx(65f) / 2
            ttooltip.pivotY = Tools.fromDpToPx(25f)
        }

        // Data
        var dataset = LineSet(labels, values)
        dataset.setColor(Color.parseColor("#758cbb"))
                .setFill(Color.parseColor("#9C152949"))
                .setDotsColor(Color.parseColor("#758cbb"))
                .setThickness(4f)
                .setDashed(floatArrayOf(10f, 10f))
                .beginAt(5)
        chart.addData(dataset)

        dataset = LineSet(labels, values)
        dataset.setColor(Color.parseColor("#b3b5bb"))
                .setFill(Color.parseColor("#9C152949"))
                .setDotsColor(Color.parseColor("#ffc755"))
                .setThickness(4f)
                .endAt(6)
        chart.addData(dataset)

        val chartAction = Runnable {
            ttooltip.prepare(chart.getEntriesArea(0).get(3), values[3])
            chart.showTooltip(ttooltip, true)
        }

        chart.setXLabels(AxisRenderer.LabelPosition.NONE)
                .setYLabels(AxisRenderer.LabelPosition.NONE)
                .setTooltips(ttooltip)
                .show(
                        Animation().setInterpolator(BounceInterpolator())
                                .fromAlpha(0)
                                .withEndAction(chartAction)
                )
    }

    private fun updateSubscribeIcon(followedByCurrentInvestor: Boolean) {

    }
}

package com.easyinvest.ui

import android.animation.PropertyValuesHolder
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Build
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.view.animation.BounceInterpolator
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.db.chart.animation.Animation
import com.db.chart.model.LineSet
import com.db.chart.renderer.AxisRenderer
import com.db.chart.tooltip.Tooltip
import com.db.chart.util.Tools
import com.easyinvest.R
import com.easyinvest.core.Feature
import com.easyinvest.data.Trader
import com.easyinvest.util.showMonthlyPercent
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.dialog_follow.view.*
import kotlinx.android.synthetic.main.trader_header.*
import java.util.*
import kotlin.math.roundToInt


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

    private val labels = arrayOf(
        "Jan",
        "Fev",
        "Mar",
        "Apr",
        "Jun",
        "May",
        "Jul",
        "Aug",
        "Sep",
        "May",
        "Jul",
        "Aug",
        "Sep"
    )

    private val rnd = Random()
    private val valuesUp = listOf(
        rnd.nextInt(3) + 1,
        rnd.nextInt(3) + 2,
        rnd.nextInt(2) + 3,
        rnd.nextInt(4) + 4,
        rnd.nextInt(3) + 5,
        rnd.nextInt(3) + 6,
        rnd.nextInt(3) + 7,
        rnd.nextInt(4) + 8,
        rnd.nextInt(3) + 9,
        rnd.nextInt(3) + 10,
        rnd.nextInt(3) + 11,
        rnd.nextInt(2) + 12,
        rnd.nextInt(1) + 13
    ).map { it.toFloat() }.toFloatArray()

    private val valuesDown = listOf(
        rnd.nextInt(1) + 13,
        rnd.nextInt(2) + 12,
        rnd.nextInt(3) + 11,
        rnd.nextInt(3) + 10,
        rnd.nextInt(3) + 9,
        rnd.nextInt(3) + 6,
        rnd.nextInt(3) + 5,
        rnd.nextInt(3) + 7,
        rnd.nextInt(4) + 8,
        rnd.nextInt(4) + 4,
        rnd.nextInt(2) + 3,
        rnd.nextInt(3) + 2,
        rnd.nextInt(3) + 1
    ).map { it.toFloat() }.toFloatArray()

    private val valuesFlat = listOf(
        5,
        rnd.nextInt(2) + 3,
        rnd.nextInt(3) + 2,
        2,
        rnd.nextInt(3) + 4,
        rnd.nextInt(3) + 3,
        rnd.nextInt(3) + 5,
        rnd.nextInt(3) + 4,
        rnd.nextInt(4) + 3,
        rnd.nextInt(4) + 3,
        rnd.nextInt(2) + 3,
        rnd.nextInt(2) + 7,
        5
    ).map { it.toFloat() }.toFloatArray()

    private val values
        get() = if (trader.profitPercentage > 0) valuesUp else if (trader.profitPercentage == 0) valuesFlat else valuesDown

    private lateinit var ttooltip: Tooltip

    private var availableToInvestMoney = 0
    private val minToInvest
        get() = (availableToInvestMoney * 0.1).roundToInt()

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trader_details)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        Feature.refresh()

        compositeDisposable.add(Feature.availableMoneyToInvest()
            .subscribe {
                availableToInvestMoney = it
            })

        compositeDisposable.add(Feature.followState(trader.id)
            .subscribe { isFollowed ->
                headerFollowButton.text = if (isFollowed) "Unfollow" else "Follow"
                headerFollowButton.setOnClickListener {
                    if (isFollowed) {
                        Toast.makeText(
                            this@TraderDetailsActivity,
                            "${trader.name} has been unfollowed!",
                            Toast.LENGTH_SHORT
                        ).show()
                        Feature.unfollow(trader.subscriptionId)
                    } else {
                        showBottomDialog()
                    }
                }

                if (isFollowed) {
                    rateLabel.visibility = View.GONE
                    description.text =
                            if (trader.profitPercentage > 0) "You get richer \uD83D\uDCB0\uD83D\uDCB0\uD83D\uDCB0" else "Next week will be better!"
                } else {
                    rateLabel.visibility = View.VISIBLE
                    description.text =
                            if (trader.profitPercentage > 0) "If you had trusted $availableToInvestMoney free money a month ago, now you would have had ${(availableToInvestMoney + (availableToInvestMoney * trader.profitPercentage / 100))} \uD83E\uDD11"
                            else "Next week will be better for them?"
                }
            })


        name.text = trader.name

        Glide.with(this)
            .load(trader.avatar)
            .apply(RequestOptions.circleCropTransform())
            .into(avatar)

        profit.showMonthlyPercent(trader.profitPercentage, this)

        profitPerMonth.visibility = if (trader.followedByCurrentInvestor) {
            View.GONE
        } else {
            View.VISIBLE
        }

        if (trader.followersCount == 0) {
            followers.text = "has no followers"
        } else {
            followers.text = "${trader.followersCount}"
        }

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

        val fillColor = ContextCompat.getColor(this, R.color.white)
        val lineColor = ContextCompat.getColor(this, R.color.colorPrimary)
        val dotsColor = ContextCompat.getColor(this, R.color.white70)

        // Data
        var dataset = LineSet(labels, values)
        dataset.setColor(lineColor)
            .setSmooth(true)
            .setDotsRadius(4f)
            .setDotsColor(dotsColor)
            .setGradientFill(intArrayOf(lineColor, fillColor), null)
            .setThickness(4f)
            .beginAt(5)
        chart.addData(dataset)

        dataset = LineSet(labels, values)
        dataset.setColor(lineColor)
            .setGradientFill(intArrayOf(lineColor, fillColor), null)
            .setSmooth(true)
            .setDotsRadius(4f)
            .setDotsColor(dotsColor)
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

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    private lateinit var dialog: BottomSheetDialog

    private fun showBottomDialog() {
        dialog = BottomSheetDialog(this@TraderDetailsActivity)
        val view = this@TraderDetailsActivity.layoutInflater.inflate(
            R.layout.dialog_follow,
            null
        )
        dialog.setContentView(view)

        view.followDialogName.text = trader.name
        Glide.with(this)
            .load(trader.avatar)
            .apply(RequestOptions.circleCropTransform())
            .into(view.followDialogAvatar)

        view.amountOfInvestment.text = "$minToInvest\$"
        view.followDialogSeekBarAmount.max = availableToInvestMoney
        view.followDialogSeekBarAmount.progress = if (minToInvest != 0) minToInvest else 1
        view.followDialogSeekBarAmount.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val MIN = 1

                if (fromUser) {
                    view.amountOfInvestment.text = "${if (progress < MIN) {
                        MIN
                    } else {
                        progress
                    }}\$"
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        view.follow.isEnabled = minToInvest > 5
        view.follow.setOnClickListener {
            Feature.follow(trader.id, view.followDialogSeekBarAmount.progress.toFloat())
            dialog.hide()
            finish()
        }

        dialog.show()
    }
}

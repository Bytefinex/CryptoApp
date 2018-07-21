package com.easyinvest.util

import android.content.Context
import android.support.v4.content.ContextCompat
import android.widget.TextView
import com.easyinvest.R
import com.easyinvest.data.Trader
import com.easyinvest.ui.setTextViewDrawableColor
import kotlin.math.absoluteValue

fun toPercent(totalMoney: Float, extraMoney: Float): Int {
    if (totalMoney == 0f && extraMoney == 0f) return 0

    return (extraMoney / (totalMoney - extraMoney) * 100).toInt()
}

fun TextView.showMonthlyPercent(trader: Trader, context: Context) {
    val colorAndIcon = if (trader.profitPercentage >= 0) {
        R.color.green to R.drawable.ic_trending_up
    } else {
        R.color.red to R.drawable.ic_trending_down
    }

    with(this) {
        setTextColor(ContextCompat.getColor(context, colorAndIcon.first))
        setCompoundDrawablesWithIntrinsicBounds(
                colorAndIcon.second,
                0,
                0,
                0
        )
        setTextViewDrawableColor(colorAndIcon.first)
        text = "${trader.profitPercentage.absoluteValue}%"
    }
}
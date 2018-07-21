package com.easyinvest.util

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import android.widget.TextView
import com.easyinvest.R
import com.easyinvest.ui.setTextViewDrawableColor
import kotlin.math.absoluteValue

fun toPercent(totalMoney: Float, extraMoney: Float): Int {
    if (totalMoney == 0f && extraMoney == 0f) return 0

    return (extraMoney / (totalMoney - extraMoney) * 100).toInt()
}

fun TextView.showMonthlyPercent(profitPercentage: Int, context: Context) {
    val colorAndIcon = if (profitPercentage >= 0) {
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
        text = "${profitPercentage.absoluteValue}%"
    }
}

fun colorifyProfit(textView: TextView, imageView: ImageView, isPositive : Boolean, displayText : String) {
    val colorAndIcon = if (isPositive) {
        R.color.green to R.drawable.ic_trending_up
    } else {
        R.color.red to R.drawable.ic_trending_down
    }

    with(textView) {
        setTextColor(ContextCompat.getColor(context, colorAndIcon.first))
        text = displayText
    }

    val drawable = ContextCompat.getDrawable(imageView.context, colorAndIcon.second)!!
    drawable.colorFilter =
            PorterDuffColorFilter(
                    ContextCompat.getColor(textView.context, colorAndIcon.first),
                    PorterDuff.Mode.SRC_IN
            )

    imageView.setImageDrawable(drawable)
}
package com.easyinvest.util

fun toPercent(totalMoney: Float, extraMoney: Float): Int {
    if (totalMoney == 0f && extraMoney == 0f) return 0

    return (extraMoney / (totalMoney - extraMoney) * 100).toInt()
}
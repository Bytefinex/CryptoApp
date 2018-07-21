package com.easyinvest.util

import junit.framework.Assert
import org.junit.Test

class UtilsTest {
    @Test
    fun totalToPercentPositive() {
        Assert.assertEquals(15, toPercent(totalMoney = 115f, extraMoney = 15f))
    }

    @Test
    fun totalToPercentNegative() {
        Assert.assertEquals(-15, toPercent(totalMoney = 85f, extraMoney = -15f))
    }
}
package com.easyinvest.base

import android.support.v4.app.Fragment
import android.widget.Toast


abstract class BaseFragment : Fragment() {
    private var toast: Toast? = null

    protected fun showNoInternetToast() {
        toast?.cancel()
        toast = Toast.makeText(context, "No connection", Toast.LENGTH_SHORT)
        toast?.show()
    }
}
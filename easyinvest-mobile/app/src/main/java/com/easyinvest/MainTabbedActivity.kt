package com.easyinvest

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.easyinvest.core.Feature
import com.easyinvest.fragments.SettingsFragment
import com.easyinvest.fragments.TradersFragment
import com.easyinvest.portfolio.PortfolioFragment
import kotlinx.android.synthetic.main.activity_main_tabbed.*

class MainTabbedActivity : AppCompatActivity() {

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        supportActionBar?.elevation = 0f
        title = item.title
        when (item.itemId) {
            R.id.navigation_home -> {
                loadFragment(PortfolioFragment.instance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                loadFragment(TradersFragment.instance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                loadFragment(SettingsFragment.instance())
                return@OnNavigationItemSelectedListener true
            }
        }

        false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_tabbed)
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        initialView()

        Feature.refresh()
    }

    private fun initialView() {
        loadFragment(PortfolioFragment.instance())
        setTitle(R.string.home_tab_title)
        supportActionBar?.elevation = 0f
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
    }
}

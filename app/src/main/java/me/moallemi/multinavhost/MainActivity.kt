package me.moallemi.multinavhost

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var currentController: NavController? = null

    private val navHomeController: NavController by lazy { findNavController(R.id.homeTab) }
    private val navDashboardController: NavController by lazy { findNavController(R.id.dashboardTab) }
    private val navNotificationController: NavController by lazy { findNavController(R.id.notificationsTab) }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        switchTab(item.itemId)
        return@OnNavigationItemSelectedListener true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if (savedInstanceState == null) {
            currentController = navHomeController
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState?.let {
            switchTab(bottomNavigationView.selectedItemId)
        }
    }

    override fun supportNavigateUpTo(upIntent: Intent) {
        currentController?.navigateUp()
    }

    override fun onBackPressed() {
        currentController?.let {
            if (it.popBackStack().not())
                finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        // Allows NavigationUI to support proper up navigation or the drawer layout
        // drawer menu, depending on the situation
        return currentController!!.navigateUp()
    }

    private fun switchTab(tabId: Int) {
        when (tabId) {
            R.id.navigation_home -> {
                currentController = navHomeController

                homeTabContainer.visibility = View.VISIBLE
                dashboardTabContainer.visibility = View.INVISIBLE
                notificationsTabContainer.visibility = View.INVISIBLE
            }
            R.id.navigation_dashboard -> {
                currentController = navDashboardController

                homeTabContainer.visibility = View.INVISIBLE
                dashboardTabContainer.visibility = View.VISIBLE
                notificationsTabContainer.visibility = View.INVISIBLE
            }
            R.id.navigation_notifications -> {
                currentController = navNotificationController

                homeTabContainer.visibility = View.INVISIBLE
                dashboardTabContainer.visibility = View.INVISIBLE
                notificationsTabContainer.visibility = View.VISIBLE
            }
        }
    }
}

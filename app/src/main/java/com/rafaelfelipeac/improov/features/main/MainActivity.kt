package com.rafaelfelipeac.improov.features.main

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.crashlytics.android.Crashlytics
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.rafaelfelipeac.improov.R
import com.rafaelfelipeac.improov.core.platform.base.BaseActivity
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet_tips_one.*
import kotlinx.android.synthetic.main.bottom_sheet_tips_three.*
import kotlinx.android.synthetic.main.bottom_sheet_tips_two.*

class MainActivity : BaseActivity() {

    lateinit var toolbar: Toolbar
    lateinit var navController: NavController
    lateinit var fakeBottomNav: View
    lateinit var navLayout: CoordinatorLayout
    lateinit var fab: FloatingActionButton

    lateinit var bottomSheetTipClose: ConstraintLayout
    lateinit var bottomSheetTip: BottomSheetBehavior<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Fabric.with(this, Crashlytics())

        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)

        setupElements()
        setupToolbar()

        NavigationUI.setupWithNavController(bottom_nav, navController)
    }

    override fun onBackPressed() {
        if (lastFragment()) {
            finish()
        } else {
            super.onBackPressed()
        }

        clearToolbarMenu()
    }

    private fun lastFragment(): Boolean {
        val currentFragment = NavHostFragment.findNavController(nav_host_fragment).currentDestination!!.id

        return currentFragment == R.id.navigation_list
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when {
            item?.itemId == R.id.menu_save -> false
            item?.itemId == R.id.menu_add -> false
            item?.itemId == android.R.id.home -> false
            else -> false
        }
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()

    private fun setupElements() {
        toolbar = findViewById(R.id.toolbar)!!
        navController = findNavController(R.id.nav_host_fragment)
        navLayout = nav_layout
        fakeBottomNav = fake_bottom_nav
        fab = fab_layout
    }

    fun setupBottomSheetTipsOne() {
        bottomSheetTip = BottomSheetBehavior.from(findViewById<LinearLayout>(R.id.tips_one))
        bottomSheetTipClose = tips_one_button_close
    }

    fun setupBottomSheetTipsTwo() {
        bottomSheetTip = BottomSheetBehavior.from(findViewById<LinearLayout>(R.id.tips_two))
        bottomSheetTipClose = tips_two_button_close
    }

    fun setupBottomSheetTipsThree() {
        bottomSheetTip = BottomSheetBehavior.from(findViewById<LinearLayout>(R.id.tips_three))
        bottomSheetTipClose = tips_three_button_close
    }

    fun openBottomSheetTips() {
        bottomSheetTip.state = BottomSheetBehavior.STATE_EXPANDED
    }

    fun closeBottomSheetTips() { bottomSheetTip.state = BottomSheetBehavior.STATE_COLLAPSED }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)

        toolbar.navigationIcon?.setColorFilter(
            ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP
        )
    }

    private fun clearToolbarMenu() = toolbar.menu!!.clear()

    fun closeToolbar() = supportActionBar?.hide()

    fun openToolbar() = supportActionBar?.show()
}
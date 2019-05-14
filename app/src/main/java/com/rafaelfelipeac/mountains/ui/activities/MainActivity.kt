package com.rafaelfelipeac.mountains.ui.activities

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.crashlytics.android.Crashlytics
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.textfield.TextInputEditText
import com.rafaelfelipeac.mountains.R
import com.rafaelfelipeac.mountains.extension.*
import com.rafaelfelipeac.mountains.models.Goal
import com.rafaelfelipeac.mountains.models.Item
import com.rafaelfelipeac.mountains.ui.base.BaseActivity
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet_goal_done.*
import kotlinx.android.synthetic.main.bottom_sheet_item_fragment.*

class MainActivity : BaseActivity() {

    lateinit var toolbar: Toolbar
    lateinit var navController: NavController
    lateinit var bottomNavigation: BottomNavigationView

    lateinit var bottomSheetItemSave: Button
    lateinit var bottomSheetItemClose: ImageView
    lateinit var bottomSheetItemName: TextInputEditText
    lateinit var bottomSheetItem: BottomSheetBehavior<*>

    lateinit var bottomSheetDoneGoalNo: Button
    lateinit var bottomSheetDoneGoalYes: Button
    lateinit var bottomSheetDoneGoal: BottomSheetBehavior<*>

    var mGoogleSignInClient: GoogleSignInClient? = null

    var item: Item? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Fabric.with(this, Crashlytics())

        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)

        setupElements()
        setupToolbar()
        setupGoogleClient()

        NavigationUI.setupWithNavController(bottom_nav, navController)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        clearToolbarMenu()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when {
            item?.itemId == R.id.menu_goal_save -> false
            item?.itemId == R.id.menu_goal_add  -> false
            item?.itemId == android.R.id.home   -> false
            else                                -> false
        }
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()

    private fun setupGoogleClient() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun setupElements() {
        toolbar = findViewById(R.id.toolbar)!!
        navController = findNavController(R.id.nav_host_fragment)
        bottomNavigation = bottom_nav

        setupBottomSheetItem()
        setupBottomSheetDoneGoal()
    }

    private fun setupBottomSheetItem() {
        bottomSheetItem = BottomSheetBehavior.from(findViewById<LinearLayout>(R.id.bottom_sheet_add_item))
        bottomSheetItemClose = bottom_sheet_item_button_close
        bottomSheetItemSave = bottom_sheet_item_button_save
        bottomSheetItemName = bottom_sheet_item_name
    }

    private fun setupBottomSheetDoneGoal() {
        bottomSheetDoneGoal = BottomSheetBehavior.from(findViewById<LinearLayout>(R.id.bottom_sheet_done_goal))
        bottomSheetDoneGoalYes = bottom_sheet_button_yes
        bottomSheetDoneGoalNo = bottom_sheet_button_no
    }

    fun bottomNavigationVisible(visibility: Int) {
        bottomNavigation.visibility = visibility
    }

    fun openBottomSheetItem(item: Item?) {
        bottomSheetItem.state = BottomSheetBehavior.STATE_EXPANDED

        this.item = item

        if (item != null) {
            bottom_sheet_item_title?.text = getString(R.string.bottom_sheet_item_title_edit)
            bottom_sheet_item_name?.setText(item.name)

            if (item.done) {
                bottom_sheet_item_date.text = String.format(getString(R.string.bottom_sheet_item_date_format), item.doneDate?.convertDateToString())
                bottom_sheet_item_date.visible()
            } else {
                bottom_sheet_item_date.gone()
            }
        } else {
            bottom_sheet_item_title?.text = getString(R.string.bottom_sheet_item_title_add)
            bottom_sheet_item_name?.resetValue()
            bottom_sheet_item_date?.resetValue()
            bottom_sheet_item_date.gone()
        }
    }

    fun closeBottomSheetItem() { bottomSheetItem.state = BottomSheetBehavior.STATE_COLLAPSED }

    fun openBottomSheetDoneGoal(goal: Goal, function: (goal: Goal) -> Unit) {
        bottomSheetDoneGoal.state = BottomSheetBehavior.STATE_EXPANDED

        bottomSheetDoneGoalYes.setOnClickListener {
            function(goal)
            closeBottomSheetDoneGoal()
        }

        bottomSheetDoneGoalNo.setOnClickListener {
            closeBottomSheetDoneGoal()
        }
    }

    fun closeBottomSheetDoneGoal() { bottomSheetDoneGoal.state = BottomSheetBehavior.STATE_COLLAPSED }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)

        toolbar.navigationIcon?.setColorFilter(
            ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP
        )
    }

    private fun clearToolbarMenu() = toolbar.menu!!.clear()
}

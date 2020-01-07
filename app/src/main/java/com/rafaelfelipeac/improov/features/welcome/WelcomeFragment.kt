package com.rafaelfelipeac.improov.features.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.rafaelfelipeac.improov.R
import com.rafaelfelipeac.improov.core.extension.invisible
import com.rafaelfelipeac.improov.core.extension.visible
import com.rafaelfelipeac.improov.core.platform.base.BaseFragment
import com.rafaelfelipeac.improov.features.main.MainActivity
import kotlinx.android.synthetic.main.fragment_welcome.*

class WelcomeFragment : BaseFragment() {

    private val welcomeViewModel by lazy { viewModelFactory.get<WelcomeViewModel>(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.welcome_title)

        hideNavigation()

        (activity as MainActivity).closeToolbar()

        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onStart() {
        super.onStart()

        if (preferences.welcome) {
            navController.navigate(WelcomeFragmentDirections.actionNavigationWelcomeToNavigationList())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        welcome_start_button.setOnClickListener {
            preferences.welcome = true

            navController.navigate(WelcomeFragmentDirections.actionNavigationWelcomeToNavigationList())
        }

        welcome_viewpager.adapter = WelcomeAdapter(this, fragmentManager!!)
        welcome_dots.setupWithViewPager(welcome_viewpager, true)
    }

    fun showStartButton() {
        welcome_start_button.visible()
    }

    fun hideStartButton() {
        welcome_start_button.invisible()
    }
}
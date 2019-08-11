package com.rafaelfelipeac.mountains.ui.fragments.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rafaelfelipeac.mountains.R
import com.rafaelfelipeac.mountains.ui.base.BaseFragment

class WelcomeThreeFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)
        
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_welcome_three, container, false)
    }
}

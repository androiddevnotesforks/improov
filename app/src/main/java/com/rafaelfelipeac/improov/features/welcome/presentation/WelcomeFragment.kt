package com.rafaelfelipeac.improov.features.welcome.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rafaelfelipeac.improov.core.extension.invisible
import com.rafaelfelipeac.improov.core.extension.observe
import com.rafaelfelipeac.improov.core.extension.viewBinding
import com.rafaelfelipeac.improov.core.extension.visible
import com.rafaelfelipeac.improov.core.platform.base.BaseFragment
import com.rafaelfelipeac.improov.databinding.FragmentWelcomeBinding

class WelcomeFragment : BaseFragment() {

    private val viewModel by lazy { viewModelProvider.welcomeViewModel() }

    private var binding by viewBinding<FragmentWelcomeBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setScreen()

        return FragmentWelcomeBinding.inflate(inflater, container, false).run {
            binding = this
            binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLayout()
        observeViewModel()
    }

    private fun setScreen() {
        hideToolbar()
        hideNavigation()
    }

    private fun setupLayout() {
        binding.welcomeStartButton.setOnClickListener {
            viewModel.saveWelcome(true)
        }

        binding.welcomeViewPager.adapter =
            WelcomeAdapter(
                this,
                parentFragmentManager
            )
        binding.welcomeDots.setupWithViewPager(binding.welcomeViewPager, true)
    }

    private fun observeViewModel() {
        viewModel.saved.observe(this) {
            navController.navigate(WelcomeFragmentDirections.welcomeToList())
        }
    }

    fun showStartButton() {
        binding.welcomeStartButton.visible()
    }

    fun hideStartButton() {
        binding.welcomeStartButton.invisible()
    }
}

package com.rafaelfelipeac.mountains.di.modules

import androidx.lifecycle.ViewModelProvider
import com.rafaelfelipeac.mountains.di.modules.viewModel.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
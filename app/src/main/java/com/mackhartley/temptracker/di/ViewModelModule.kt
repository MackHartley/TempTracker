package com.mackhartley.temptracker.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mackhartley.temptracker.ui.addFever.AddFeverViewModel
import com.mackhartley.temptracker.ui.fevers.FeversViewModel
import com.squareup.anvil.annotations.ContributesTo
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
@ContributesTo(AppScope::class)
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(FeversViewModel::class)
    internal abstract fun bindFeversViewModel(feversViewModel: FeversViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddFeverViewModel::class)
    internal abstract fun bindAddFeverViewModel(addFeverViewModel: AddFeverViewModel): ViewModel
}
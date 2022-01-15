package com.mackhartley.temptracker.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mackhartley.temptracker.ui.addfever.AddFeverViewModel
import com.mackhartley.temptracker.ui.addtemp.AddEditTempViewModel
import com.mackhartley.temptracker.ui.feverdetails.FeverDetailsViewModel
import com.mackhartley.temptracker.ui.feverhistory.FeverHistoryViewModel
import com.mackhartley.temptracker.ui.fevers.FeversViewModel
import com.mackhartley.temptracker.ui.settings.SettingsViewModel
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

    @Binds
    @IntoMap
    @ViewModelKey(FeverDetailsViewModel::class)
    internal abstract fun bindFeverDetailsViewModel(feverDetailsViewModel: FeverDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddEditTempViewModel::class)
    internal abstract fun bindAddEditTempViewModel(addEditTempViewModel: AddEditTempViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FeverHistoryViewModel::class)
    internal abstract fun bindFeverHistoryViewModel(feverHistoryViewModel: FeverHistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    internal abstract fun bindSettingsViewModel(settingsViewModel: SettingsViewModel): ViewModel
}
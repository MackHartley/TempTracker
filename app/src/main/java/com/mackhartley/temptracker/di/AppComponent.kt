package com.mackhartley.temptracker.di

import android.content.Context
import com.mackhartley.temptracker.data.AppDao
import com.mackhartley.temptracker.data.AppDataBase
import com.mackhartley.temptracker.ui.addfever.AddFeverDialog
import com.mackhartley.temptracker.ui.feverdetails.FeverDetailsFragment
import com.mackhartley.temptracker.ui.fevers.FeversFragment
import com.squareup.anvil.annotations.ContributesTo
import com.squareup.anvil.annotations.MergeComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@MergeComponent(AppScope::class)
interface AppComponent : ComponentInterface {
    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun application(context: Context): Builder
    }
}

@Module
@ContributesTo(AppScope::class)
class DataModule {
    @Singleton
    @Provides
    fun providesAppDataBase(context: Context): AppDataBase {
        return AppDataBase.getDatabase(context)
    }
    @Singleton
    @Provides
    fun providesAppDao(database: AppDataBase): AppDao {
        return database.getDao()
    }
}

@ContributesTo(AppScope::class)
interface ComponentInterface {
    fun inject(feversFragment: FeversFragment)
    fun inject(addFeverDialog: AddFeverDialog)
    fun inject(feverDetailsFragment: FeverDetailsFragment)
}
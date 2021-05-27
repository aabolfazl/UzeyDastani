package me.akay.uzaydestan.dependencyInjection

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.akay.uzaydestan.main.MainFragment

@Module
abstract class FragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment
}
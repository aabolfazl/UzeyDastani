package me.akay.uzaydestan.dependencyInjection

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.akay.uzaydestan.main.MainFragment
import me.akay.uzaydestan.spacecraft.SpacecraftFragment

@Module
abstract class FragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun contributeSpaceCraftFragment(): SpacecraftFragment
}
package me.akay.uzaydestan.dependencyInjection

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.akay.uzaydestan.MainActivity

@Module
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}
package me.akay.uzaydestan

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import me.akay.uzaydestan.dependencyInjection.DaggerApplicationComponent

class ApplicationLoader : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication?> {
        return DaggerApplicationComponent.builder()
            .application(this)
            .build()
    }
}
package me.akay.uzaydestan.dependencyInjection

import dagger.Module
import me.akay.uzaydestan.api.NetworkModule
import me.akay.uzaydestan.main.MainModule

@Module(
    includes = [NetworkModule::class, MainModule::class, ActivityBuilderModule::class, FragmentBuilderModule::class]
)
class ApplicationModule {

}
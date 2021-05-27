package me.akay.uzaydestan.dependencyInjection

import dagger.Module
import dagger.Provides
import me.akay.uzaydestan.api.ApiService
import me.akay.uzaydestan.api.NetworkModule
import me.akay.uzaydestan.main.MainModule
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(
    includes = [NetworkModule::class, MainModule::class, ActivityBuilderModule::class, FragmentBuilderModule::class]
)
class ApplicationModule {
    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
package me.akay.uzaydestan.dependencyInjection

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import me.akay.uzaydestan.ApplicationLoader
import me.akay.uzaydestan.api.ApiService
import me.akay.uzaydestan.api.NetworkModule
import me.akay.uzaydestan.data.ApplicationDatabase
import me.akay.uzaydestan.data.SpaceStationDAO
import me.akay.uzaydestan.data.SpacecraftDAO
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [
    NetworkModule::class,
    ViewModelModule::class,
    ActivityBuilderModule::class,
    FragmentBuilderModule::class
])
class ApplicationModule {
    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideDatabase(context: Context): ApplicationDatabase {
        return Room.databaseBuilder(context, ApplicationDatabase::class.java, "uzay-db")
                .fallbackToDestructiveMigration()
                .build()
    }

    @Singleton
    @Provides
    fun provideSpacecraftDao(db: ApplicationDatabase): SpacecraftDAO {
        return db.spacecraftDao()
    }

    @Singleton
    @Provides
    fun provideSpaceStationDao(db: ApplicationDatabase): SpaceStationDAO {
        return db.spaceStationDAO()
    }

    @Singleton
    @Provides
    fun provideContext(application: ApplicationLoader): Context {
        return application.applicationContext
    }
}

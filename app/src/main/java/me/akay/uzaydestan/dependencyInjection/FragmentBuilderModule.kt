package me.akay.uzaydestan.dependencyInjection

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.akay.uzaydestan.favorite.FavoriteFragment
import me.akay.uzaydestan.spacecraft.SpacecraftFragment
import me.akay.uzaydestan.stations.StationFragment

@Module
abstract class FragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeStationFragment(): StationFragment

    @ContributesAndroidInjector
    abstract fun contributeSpaceCraftFragment(): SpacecraftFragment

    @ContributesAndroidInjector
    abstract fun contributeFavoriteFragment(): FavoriteFragment
}
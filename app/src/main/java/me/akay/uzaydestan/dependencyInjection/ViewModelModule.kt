package me.akay.uzaydestan.dependencyInjection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import me.akay.uzaydestan.spacecraft.SpaceCraftViewModel
import me.akay.uzaydestan.stations.StationViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SpaceCraftViewModel::class)
    abstract fun bindSpaceCraftViewModel(stationViewModel: SpaceCraftViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StationViewModel::class)
    abstract fun bindStationViewModel(stationViewModel: StationViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
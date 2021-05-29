package me.akay.uzaydestan.stations

import me.akay.uzaydestan.data.SpaceStationEntity

interface StationAdapterDelegate {
    fun onButtonClicked(station: SpaceStationEntity)
    fun onFavoriteClicked(station: SpaceStationEntity)
}
package me.akay.uzaydestan.favorite

import me.akay.uzaydestan.data.SpaceStationEntity

interface FavoriteAdapterDelegate {
    fun onFavoriteClicked(station: SpaceStationEntity)
}
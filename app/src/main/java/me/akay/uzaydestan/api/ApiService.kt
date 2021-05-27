package me.akay.uzaydestan.api

import io.reactivex.Single
import me.akay.uzaydestan.datamodels.SpaceStation
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("{id}")
    fun getSpaceStations(@Path("id") id: String): Single<List<SpaceStation>>
}
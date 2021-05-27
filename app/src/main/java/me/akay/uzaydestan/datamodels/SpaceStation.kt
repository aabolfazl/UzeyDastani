package me.akay.uzaydestan.datamodels

import com.google.gson.annotations.SerializedName

class SpaceStation(@SerializedName("name") val name: String, @SerializedName("coordinateX") val coordinateX: Float, @SerializedName("coordinateY") val coordinateY: Float, @SerializedName("capacity") val capacity: Int, @SerializedName("stock") val stock: Int, @SerializedName("need") val need: Int)
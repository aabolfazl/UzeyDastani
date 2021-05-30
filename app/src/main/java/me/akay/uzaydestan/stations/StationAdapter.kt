package me.akay.uzaydestan.stations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.akay.uzaydestan.R
import me.akay.uzaydestan.data.SpaceStationEntity
import java.util.*
import kotlin.collections.ArrayList

class StationAdapter constructor(val stationAdapterDelegate: StationAdapterDelegate) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private companion object {
        const val EARTH_STATION = 0
        const val OTHER_STATION = 1
    }

    private var spaceStations: List<SpaceStationEntity> = ArrayList()

    fun setStations(list: List<SpaceStationEntity>) {
        if (list.isEmpty()) return

        spaceStations = list
        notifyDataSetChanged()
    }

    fun getStationsSize(): Int = spaceStations.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == EARTH_STATION) {
            EarthViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_earth, parent, false))
        } else {
            StationViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_station, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == EARTH_STATION) {
            (holder as EarthViewHolder).bind(spaceStations[position])
        } else {
            (holder as StationViewHolder).bind(spaceStations[position])
        }
    }

    override fun getItemCount(): Int {
        return getStationsSize()
    }

    override fun getItemViewType(position: Int): Int {
        return if (spaceStations[position].isEarth()) EARTH_STATION else OTHER_STATION
    }

    inner class StationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameTextView: TextView = itemView.findViewById(R.id.station_name_textView)
        private val capacityTextView: TextView = itemView.findViewById(R.id.station_ugs_capacity_textView)
        private val distanceTextView: TextView = itemView.findViewById(R.id.station_distance_textView)
        private val favoriteImageView: ImageView = itemView.findViewById(R.id.station_favorite_imageView)
        private val travelButton: Button = itemView.findViewById(R.id.station_travel_button)

        fun bind(station: SpaceStationEntity) {
            nameTextView.text = station.name
            capacityTextView.text = station.capacity.toString()
            travelButton.visibility = if (station.missionComplete) View.INVISIBLE else View.VISIBLE

            val res = if (station.isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_outline
            favoriteImageView.setImageResource(res)

            travelButton.setOnClickListener { stationAdapterDelegate.onButtonClicked(station) }
            favoriteImageView.setOnClickListener { stationAdapterDelegate.onFavoriteClicked(station) }

            distanceTextView.text = String.format(Locale.US, "%.3f EUS", station.distanceToCurrent)
        }
    }

    inner class EarthViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameTextView: TextView = itemView.findViewById(R.id.tv_station_name)
        private val favoriteImageView: ImageView = itemView.findViewById(R.id.iv_station_favorite)

        fun bind(station: SpaceStationEntity) {
            nameTextView.text = station.name

            val res = if (station.isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_outline
            favoriteImageView.setImageResource(res)

            favoriteImageView.setOnClickListener { stationAdapterDelegate.onFavoriteClicked(station) }
        }
    }
}
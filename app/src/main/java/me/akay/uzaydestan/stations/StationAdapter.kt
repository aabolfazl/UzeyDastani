package me.akay.uzaydestan.stations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.akay.uzaydestan.R
import me.akay.uzaydestan.datamodels.SpaceStation

class StationAdapter : RecyclerView.Adapter<StationAdapter.StationViewHolder>() {
    private var spaceStations: List<SpaceStation> = ArrayList();

    fun setStations(list: List<SpaceStation>) {
        spaceStations = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_station, parent, false)
        return StationViewHolder(view)
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        holder.bind(spaceStations[position])
    }

    override fun getItemCount(): Int {
        return spaceStations.size
    }

    class StationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameTextView: TextView = itemView.findViewById(R.id.station_name_textView)
        private val capacityTextView: TextView = itemView.findViewById(R.id.station_ugs_capacity_textView)
        private val distanceTextView: TextView = itemView.findViewById(R.id.station_distance_textView)
        private val favoriteImageView: ImageView = itemView.findViewById(R.id.station_favorite_imageView)
        private val travelButton: Button = itemView.findViewById(R.id.station_travel_button)

        fun bind(station: SpaceStation) {
            nameTextView.text = station.name
            capacityTextView.text = station.capacity.toString()
        }
    }
}
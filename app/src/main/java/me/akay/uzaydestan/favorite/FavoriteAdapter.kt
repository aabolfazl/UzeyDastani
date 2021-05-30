package me.akay.uzaydestan.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.akay.uzaydestan.R
import me.akay.uzaydestan.data.SpaceStationEntity

class FavoriteAdapter constructor(val delegate: FavoriteAdapterDelegate) :
    RecyclerView.Adapter<FavoriteAdapter.StationViewHolder>() {

    private var favoriteList: List<SpaceStationEntity> = ArrayList()

    fun setStations(list: List<SpaceStationEntity>) {
        favoriteList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return StationViewHolder(view)
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        holder.bind(favoriteList[position])
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }

    inner class StationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameTextView: TextView = itemView.findViewById(R.id.favorite_name_textView)
        private val capacityTextView: TextView = itemView.findViewById(R.id.favorite_ugs_capacity_textView)
        private val distanceTextView: TextView = itemView.findViewById(R.id.favorite_distance_textView)
        private val favoriteImageView: ImageView = itemView.findViewById(R.id.iv_favorite)

        fun bind(station: SpaceStationEntity) {
            nameTextView.text = station.name
            capacityTextView.text = station.capacity.toString()

            val res = if (station.isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_outline
            favoriteImageView.setImageResource(res)
            favoriteImageView.setOnClickListener { delegate.onFavoriteClicked(station) }
        }
    }
}
package me.akay.uzaydestan.stations

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.DaggerFragment
import me.akay.uzaydestan.R
import me.akay.uzaydestan.data.SpaceStationEntity
import me.akay.uzaydestan.data.SpacecraftEntity
import me.akay.uzaydestan.helper.AndroidUtils
import me.akay.uzaydestan.helper.Status
import me.akay.uzaydestan.stations.RecyclerViewSnapHelper.OnSelectedItemChange
import javax.inject.Inject

class StationFragment : DaggerFragment(), StationAdapterDelegate {
    private val TAG = "MainFragment"

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: StationViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var spacecraftNameTextView: TextView
    private lateinit var spacecraftDamageTextView: TextView
    private lateinit var currentStationTextView: TextView
    private lateinit var UGSTextView: TextView
    private lateinit var EUSTextView: TextView
    private lateinit var DSTextView: TextView
    private lateinit var counterTextView: TextView
    private lateinit var currentStationFavImageView: ImageView
    private lateinit var progressBarView: View
    private lateinit var tryAgainView: View
    private lateinit var forwardView: View
    private lateinit var backView: View

    private var adapter: StationAdapter = StationAdapter(this)
    private var currentPosition: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_station, container, false)

        val snapHelper = RecyclerViewSnapHelper(object : OnSelectedItemChange {
            override fun onSelectedItemChange(position: Int) {
                currentPosition = position
            }
        })

        spacecraftNameTextView = view.findViewById(R.id.tv_station_spacecraft)
        spacecraftDamageTextView = view.findViewById(R.id.main_spacecraft_damage)
        currentStationTextView = view.findViewById(R.id.tv_station_current)
        UGSTextView = view.findViewById(R.id.tc_station_UGS)
        EUSTextView = view.findViewById(R.id.tc_station_EUS)
        DSTextView = view.findViewById(R.id.tv_station_DS)
        counterTextView = view.findViewById(R.id.tv_station_counter)

        progressBarView = view.findViewById(R.id.pb_station)

        recyclerView = view.findViewById(R.id.station_recyclerView)
        recyclerView.adapter = adapter
        snapHelper.attachToRecyclerView(recyclerView)

        forwardView = view.findViewById(R.id.iv_station_arrow_forward)
        forwardView.setOnClickListener { goNextStation() }

        backView = view.findViewById(R.id.iv_station_arrow_back)
        backView.setOnClickListener { goPreviousStation() }

        tryAgainView = view.findViewById(R.id.tv_station_tryAgain)
        tryAgainView.setOnClickListener { viewModel.getStationsList() }

        currentStationFavImageView = view.findViewById(R.id.iv_station_current_favorite)
        currentStationFavImageView.setOnClickListener { onFavoriteClicked(viewModel.currentSpaceStations.value?.data!!) }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.spacecraftEntityLiveData.observe(viewLifecycleOwner, { spacecraft ->
            if (spacecraft.status == Status.SUCCESS) {
                bindSpaceCraft(spacecraft.data)
            }
        })

        viewModel.spaceStations.observe(viewLifecycleOwner, { result ->
            Log.i(TAG, "onViewCreated: " + result.status)
            when (result.status) {
                Status.SUCCESS -> {
                    adapter.setStations(result.data!!)
                    if (progressBarView.visibility != View.GONE && result.data.isNotEmpty()) {
                        progressBarView.visibility = View.GONE
                        currentStationFavImageView.visibility = View.VISIBLE
                        recyclerView.animate().setDuration(100).alpha(1f).start()
                        backView.animate().setDuration(100).alpha(1f).start()
                        forwardView.animate().setDuration(100).alpha(1f).start()
                    }
                }
                Status.ERROR -> {
                    progressBarView.visibility = View.GONE
                    tryAgainView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBarView.visibility = View.VISIBLE
                    tryAgainView.visibility = View.GONE
                }
            }
        })

        viewModel.currentSpaceStations.observe(viewLifecycleOwner, { result ->
            if (result.status == Status.SUCCESS) {
                currentStationTextView.text = result.data?.name
                val res = if (result.data!!.isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_outline
                currentStationFavImageView.setImageResource(res)

                currentStationTextView.visibility = View.VISIBLE
                currentStationFavImageView.visibility = View.VISIBLE
            }
        })

        viewModel.timerLiveData.observe(viewLifecycleOwner, { time ->
            counterTextView.text = String.format("%ds", time)
            if (time <= 3) {
                AndroidUtils.shakeView(counterTextView, 5, 0)

                if (time == 0L) {
                    Toast.makeText(context, "Back to Earth!", Toast.LENGTH_SHORT).show()
                    scrollTpStation(0)
                }
            }

        })

        viewModel.errorLiveData.observe(viewLifecycleOwner, { res ->
            Toast.makeText(context, res, Toast.LENGTH_SHORT).show()
        })
    }

    private fun bindSpaceCraft(spacecraft: SpacecraftEntity?) {
        if (spacecraft != null) {
            spacecraftNameTextView.text = spacecraft.name
            spacecraftDamageTextView.text = spacecraft.damage.toString()
            EUSTextView.text = String.format("EUS:%.2f", spacecraft.EUS)
            UGSTextView.text = String.format("UGS:%d", spacecraft.UGS)
            DSTextView.text = String.format("DS:%d", spacecraft.DS)
        }
    }

    private fun goNextStation() {
        val newPos = currentPosition + 1
        if (newPos < adapter.getStationsSize()) {
            scrollTpStation(newPos)
        }
    }

    private fun goPreviousStation() {
        val newPos = currentPosition - 1
        if (newPos >= 0) {
            scrollTpStation(newPos)
        }
    }

    private fun scrollTpStation(pos: Int, animate: Boolean = true) {
        if (animate) {
            recyclerView.smoothScrollToPosition(pos)
        } else {
            recyclerView.scrollToPosition(pos)
        }
        currentPosition = pos
    }

    override fun onButtonClicked(station: SpaceStationEntity) {
        viewModel.travelToStation(station)
    }

    override fun onFavoriteClicked(station: SpaceStationEntity) {
        viewModel.didChangeFav(station)
    }
}
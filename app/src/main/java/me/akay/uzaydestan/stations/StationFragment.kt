package me.akay.uzaydestan.stations

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.DaggerFragment
import me.akay.uzaydestan.R
import me.akay.uzaydestan.data.Spacecraft
import me.akay.uzaydestan.helper.Status
import me.akay.uzaydestan.stations.RecyclerViewSnapHelper.OnSelectedItemChange
import javax.inject.Inject

class StationFragment : DaggerFragment() {
    private val TAG = "MainFragment"

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: StationViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var spacecraftNameTextView: TextView
    private lateinit var spacecraftDamageTextView: TextView

    private var adapter: StationAdapter = StationAdapter()
    private var currentPosition: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_station, container, false)

        val snapHelper = RecyclerViewSnapHelper(object : OnSelectedItemChange {
            override fun onSelectedItemChange(position: Int) {
                Log.i(TAG, "onSelectedItemChange: $position")
                currentPosition = position
            }
        })

        spacecraftNameTextView = view.findViewById(R.id.main_spaceCraft_name)
        spacecraftDamageTextView = view.findViewById(R.id.main_spacecraft_damage)

        recyclerView = view.findViewById(R.id.main_recyclerView)
        recyclerView.adapter = adapter
        snapHelper.attachToRecyclerView(recyclerView)

        val forwardView: View = view.findViewById(R.id.main_arrow_forward)
        forwardView.setOnClickListener { goNextStation() }

        val backView: View = view.findViewById(R.id.main_arrow_back)
        backView.setOnClickListener { goPreviousStation() }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.spacecraftLiveData.observe(viewLifecycleOwner, { spacecraft ->
            bindSpaceCraft(spacecraft)
        })

        viewModel.spaceStations.observe(viewLifecycleOwner, { result ->
            Log.i(TAG, "onViewCreated: ")
            when (result.status) {
                Status.SUCCESS -> {
                    adapter.setStations(result.data!!)
                }
                Status.ERROR -> {

                }
                Status.LOADING -> {

                }
            }
        })
    }

    private fun bindSpaceCraft(spacecraft: Spacecraft?) {
        if (spacecraft != null) {
            spacecraftNameTextView.text = spacecraft.name
            spacecraftDamageTextView.text = spacecraft.damage.toString()
        }
    }

    private fun goNextStation() {
        val newPos = currentPosition + 1
//        if (newPos < viewModel.spaceStations.value?.size ?: 0) {
//            scrollTpStation(newPos)
//        }
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
}
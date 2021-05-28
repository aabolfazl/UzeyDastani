package me.akay.uzaydestan.stations

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.DaggerFragment
import me.akay.uzaydestan.R
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
    private var adapter: StationAdapter = StationAdapter()
    private var currentPosition: Int = 0
    private var stationSize: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadSpaceStationList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val snapHelper = RecyclerViewSnapHelper(object : OnSelectedItemChange {
            override fun onSelectedItemChange(position: Int) {
                Log.i(TAG, "onSelectedItemChange: $position")
                currentPosition = position
            }
        })

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

        viewModel.spaceStations.observe(viewLifecycleOwner, {
            adapter.setStations(it)
            stationSize = it.size
        })
    }

    private fun goNextStation() {
        val newPos = currentPosition + 1
        if (newPos < stationSize) {
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
}
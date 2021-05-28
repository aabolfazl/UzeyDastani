package me.akay.uzaydestan.main

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
import me.akay.uzaydestan.datamodels.SpaceStation
import me.akay.uzaydestan.main.RecyclerViewSnapHelper.OnSelectedItemChange
import javax.inject.Inject

class MainFragment : DaggerFragment() {
    private val TAG = "MainFragment"

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MainViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StationAdapter
    private var currentPosition: Int = 0
    private var stationSize: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i(TAG, "onCreate: ")

        val listOf = listOf(
            SpaceStation("Abolfazl", 4f, 6f, 500, 5222, 52),
            SpaceStation("Abbasi", 4f, 6f, 605, 5222, 52),
            SpaceStation("Dar", 4f, 6f, 23423, 5222, 52),
            SpaceStation("Akay", 4f, 6f, 463, 5222, 52),
            SpaceStation("Bekliyorom", 4f, 6f, 1247, 5222, 52),
            SpaceStation("Soyle", 4f, 6f, 57446, 5222, 52),
        )

        adapter = StationAdapter(listOf)
        stationSize = listOf.size

        viewModel.load()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
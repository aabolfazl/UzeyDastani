package me.akay.uzaydestan.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.DaggerFragment
import me.akay.uzaydestan.R
import me.akay.uzaydestan.data.SpaceStationEntity
import me.akay.uzaydestan.helper.Status
import javax.inject.Inject

class FavoriteFragment : DaggerFragment(), FavoriteAdapterDelegate {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyViewIv: View
    private lateinit var emptyViewTv: TextView

    private var adapter: FavoriteAdapter = FavoriteAdapter(this)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: FavoriteViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        emptyViewIv = view.findViewById(R.id.iv_favorite_empty)
        emptyViewTv = view.findViewById(R.id.tv_favorite_empty)

        recyclerView = view.findViewById(R.id.favorite_recycler)
        recyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.favoriteLiveData.observe(viewLifecycleOwner, { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    adapter.setStations(result.data!!)

                    if (result.data.isNotEmpty() && emptyViewIv.visibility != View.GONE) {
                        emptyViewIv.animate().setDuration(100).alpha(0f).start()
                        emptyViewTv.animate().setDuration(100).alpha(0f).start()
                        recyclerView.animate().setDuration(100).alpha(1f).start()
                    } else if (result.data.isEmpty()) {
                        emptyViewIv.animate().setDuration(100).alpha(1f).start()
                        emptyViewTv.animate().setDuration(100).alpha(1f).start()
                    }
                }

                Status.LOADING -> {
                }

                Status.ERROR -> {
                    emptyViewTv.text = getString(R.string.favorite_error)
                }
            }
        })
    }

    override fun onFavoriteClicked(station: SpaceStationEntity) {
        viewModel.didChangeFav(station)
    }
}
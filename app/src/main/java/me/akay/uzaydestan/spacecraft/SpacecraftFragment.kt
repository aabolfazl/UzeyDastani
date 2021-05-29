package me.akay.uzaydestan.spacecraft

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.android.support.DaggerFragment
import me.akay.uzaydestan.R
import me.akay.uzaydestan.helper.AndroidUtils
import me.akay.uzaydestan.helper.Resource
import me.akay.uzaydestan.helper.SeekBarListener
import me.akay.uzaydestan.helper.Status
import javax.inject.Inject

class SpacecraftFragment : DaggerFragment() {
    private lateinit var scoreTextView: TextView
    private lateinit var spaceCraftNameEditText: EditText
    private lateinit var durabilitySeekBar: SeekBar
    private lateinit var speedSeekBar: SeekBar
    private lateinit var capacitySeekBar: SeekBar
    private lateinit var letsGoButton: Button

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: SpaceCraftViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_spacecraft, container, false)

        scoreTextView = view.findViewById(R.id.tv_spacecraft_score)
        spaceCraftNameEditText = view.findViewById(R.id.et_spacecraft_name)

        durabilitySeekBar = view.findViewById(R.id.sb_spacecraft_durability)
        durabilitySeekBar.setOnSeekBarChangeListener(object : SeekBarListener() {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.durability = progress
            }
        })
        speedSeekBar = view.findViewById(R.id.sb_spacecraft_speed)
        speedSeekBar.setOnSeekBarChangeListener(object : SeekBarListener() {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.speed = progress
            }
        })
        capacitySeekBar = view.findViewById(R.id.sb_spacecraft_capacity)
        capacitySeekBar.setOnSeekBarChangeListener(object : SeekBarListener() {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.capacity = progress
            }
        })

        letsGoButton = view.findViewById(R.id.btn_spacecraft_letsGo)
        letsGoButton.setOnClickListener { onLetsGoButtonClicked() }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        durabilitySeekBar.progress = viewModel.durability
        speedSeekBar.progress = viewModel.speed
        capacitySeekBar.progress = viewModel.capacity

        viewModel.scoreLiveData.observe(viewLifecycleOwner, { score ->
            scoreTextView.text = "$score"
        })

        viewModel.saveSpace.observe(viewLifecycleOwner, this::processSaveSpacecraft)
    }

    private fun onLetsGoButtonClicked() = when {
        spaceCraftNameEditText.text.isEmpty() -> {
            AndroidUtils.shakeView(spaceCraftNameEditText, 5, 0)
        }
        viewModel.currentScore() > 15 -> {
            AndroidUtils.shakeView(scoreTextView, 5, 0)
        }
        viewModel.durability == 0 -> {
            AndroidUtils.shakeView(durabilitySeekBar, 5, 0)
        }
        viewModel.speed == 0 -> {
            AndroidUtils.shakeView(speedSeekBar, 5, 0)
        }
        viewModel.capacity == 0 -> {
            AndroidUtils.shakeView(capacitySeekBar, 5, 0)
        }
        else -> {
            viewModel.saveSpaceCraft(spaceCraftNameEditText.text.toString())
        }
    }

    private fun processSaveSpacecraft(resource: Resource<Boolean?>) {
        when (resource.status) {
            Status.LOADING -> {

            }
            Status.SUCCESS -> {
                findNavController().navigate(R.id.mainFragment)
            }
            Status.ERROR -> {
                Toast.makeText(context, resource.message, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
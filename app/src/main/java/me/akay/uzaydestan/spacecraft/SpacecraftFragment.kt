package me.akay.uzaydestan.spacecraft

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import dagger.android.support.DaggerFragment
import me.akay.uzaydestan.R

class SpacecraftFragment : DaggerFragment() {
    private lateinit var scoreTextView: TextView
    private lateinit var spaceCraftNameEditText: EditText
    private lateinit var durabilitySeekBar: SeekBar
    private lateinit var speedSeekBar: SeekBar
    private lateinit var capacitySeekBar: SeekBar
    private lateinit var letsGoButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_spacecraft, container, false)

        scoreTextView = view.findViewById(R.id.main_damage_capacity)
        spaceCraftNameEditText = view.findViewById(R.id.et_spacecraft_name)

        durabilitySeekBar = view.findViewById(R.id.sb_spacecraft_durability)
        speedSeekBar = view.findViewById(R.id.sb_spacecraft_speed)
        capacitySeekBar = view.findViewById(R.id.sb_spacecraft_capacity)

        letsGoButton = view.findViewById(R.id.btn_spacecraft_letsGo)
        letsGoButton.setOnClickListener { onLetsGoButtonClicked() }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    private fun onLetsGoButtonClicked() {

    }

}
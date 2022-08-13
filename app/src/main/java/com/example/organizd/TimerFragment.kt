package com.example.organizd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment

class TimerFragment : Fragment(R.layout.fragment_timer) {

    //Toast.makeText(this@TimerFragment.requireActivity(), "saving", Toast.LENGHT_SHORT).show()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_timer, container, false)
    }

    override fun onViewCreated(view: View , savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        var info = view.findViewById<ImageView>(R.id.infoIcon)

        info.setOnClickListener{
            Toast.makeText(this@TimerFragment.requireActivity(), "La funzione total focus permettere di silenziare qualunque notifica per migliorare la tua concentrazione.", Toast.LENGTH_LONG).show()
        }
    }

}


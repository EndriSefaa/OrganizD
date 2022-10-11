package com.example.organizd
import android.app.UiModeManager.MODE_NIGHT_NO
import android.app.UiModeManager.MODE_NIGHT_YES
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.organizd.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater)
        val view = binding.root


        binding.themeSwitch.setOnCheckedChangeListener{ buttonView, isChecked ->

            if(isChecked)
            {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            }
            else
            {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            }

        }



        return view
    }



}
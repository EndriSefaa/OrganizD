package com.example.organizd

import android.app.UiModeManager.MODE_NIGHT_NO
import android.app.UiModeManager.MODE_NIGHT_YES
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.organizd.databinding.FragmentProfileBinding


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    lateinit var binding: FragmentProfileBinding
    //lateinit var edt1: EditText
    lateinit var testo: String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater)
        val view = binding.root

        // ++++++++ Dichiarazione SharedPreferences per nome utente +++++++++
        var pref = this@ProfileFragment.requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        var SavedName = pref.getString("NICKNAME", "")
        binding.MyName.setText(SavedName)
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


        // Toast info livello
        binding.infoIconLevel.setOnClickListener{
            Toast.makeText(this@ProfileFragment.requireActivity(), "The level corresponds to the completed tasks.", Toast.LENGTH_SHORT).show()
        }


        // Switch tema scuro

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



        // Assegnazione immagine profilo in base al livello


        var savedLevel = pref.getInt("LEVEL", 0)
        binding.textLevel.setText(savedLevel.toString())

        when(savedLevel)
        {
            in 0..9 -> Glide.with(this).load(R.drawable.level_0to9).into(binding.profileImage)
            in 10..24 -> Glide.with(this).load(R.drawable.level_10to24).into(binding.profileImage)
            in 25..49 -> Glide.with(this).load(R.drawable.level_25to49).into(binding.profileImage)
            in 50..99 -> Glide.with(this).load(R.drawable.level_50to99).into(binding.profileImage)
            in 100..199 -> Glide.with(this).load(R.drawable.level_100to199).into(binding.profileImage)
            in 200..299 -> Glide.with(this).load(R.drawable.level_200to299).into(binding.profileImage)
            in 300..499 -> Glide.with(this).load(R.drawable.level_300to499).into(binding.profileImage)
            in 500..799 -> Glide.with(this).load(R.drawable.level_500to799).into(binding.profileImage)
            in 800..1199 -> Glide.with(this).load(R.drawable.level_800to1199).into(binding.profileImage)
            in 1200..1699 -> Glide.with(this).load(R.drawable.level_1200to1699).into(binding.profileImage)
            in 1700..2299 -> Glide.with(this).load(R.drawable.level_1700to2299).into(binding.profileImage)
            else -> Glide.with(this).load(R.drawable.level_3000).into(binding.profileImage)
        }


        // Modifica del nome utente nel fragment profile

        binding.MyName.setOnKeyListener(View.OnKeyListener{v,keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){

                var NickName: String = binding.MyName.getText().toString().trim()

                pref = this.requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
                pref.edit().putString("NICKNAME", NickName).apply()

                SavedName = pref.getString("NICKNAME", "")
                binding.MyName.setText(SavedName)

                Toast.makeText(this.requireActivity(), "Name saved", Toast.LENGTH_SHORT).show()
                return@OnKeyListener true
            }
            false
        })


        return view
    }


}
package com.example.organizd

import android.app.Dialog
import android.app.NotificationManager
import android.app.UiModeManager.MODE_NIGHT_NO
import android.app.UiModeManager.MODE_NIGHT_YES
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.organizd.databinding.FragmentProfileBinding


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    lateinit var binding: FragmentProfileBinding

    lateinit var pref: SharedPreferences // SharedPreferences utilizzata poi per memorizzare il nome.


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater)
        val view = binding.root

        // ++++++++ Inizializzazione SharedPreferences per nome utente +++++++++
        pref = this@ProfileFragment.requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        var SavedName = pref.getString("NICKNAME", "")
        print("Il nome: " + SavedName)
        binding.MyName.setText(SavedName)
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


        // Toast info livello
        binding.infoIconLevel.setOnClickListener{
            Toast.makeText(this@ProfileFragment.requireActivity(), "The level corresponds to the completed tasks.", Toast.LENGTH_SHORT).show()
        }




        // Info toast
        binding.infoIcon.setOnClickListener{
            val  dialogBinding = layoutInflater.inflate(R.layout.focus_dialog, null)

            val myDialog = Dialog(requireContext())
            myDialog.setContentView(dialogBinding)

            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


            myDialog.show()
        }


        // Prova attivazione modalità non disturbare.
        val mNotificationManager = activity!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        binding.switchNotDisturb.setOnCheckedChangeListener{ buttonView, isChecked ->

            if(isChecked)
            {
                if (!mNotificationManager.isNotificationPolicyAccessGranted) {
                    val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
                    startActivity(intent)
                }
            }

        }





        // Assegnazione immagine profilo in base al livello

        var savedLevel = pref.getInt("LEVEL", 0)
        var notDone = pref.getInt("NOTDONE",0)
        binding.textDone.text = "Tasks done: " + savedLevel.toString()


        binding.textUndone.text = "Tasks not done: " + notDone.toString()

        when(savedLevel)
        {
            in 0..9 -> {Glide.with(this).load(R.drawable.level_0to9).into(binding.profileImage)
                        binding.textLevel.setText("1")}
            in 10..24 -> {Glide.with(this).load(R.drawable.level_10to24).into(binding.profileImage)
                binding.textLevel.setText("2")}
            in 25..49 -> {Glide.with(this).load(R.drawable.level_25to49).into(binding.profileImage)
                binding.textLevel.setText("3")}
            in 50..99 -> {Glide.with(this).load(R.drawable.level_50to99).into(binding.profileImage)
                binding.textLevel.setText("4")}
            in 100..199 -> {Glide.with(this).load(R.drawable.level_100to199).into(binding.profileImage)
                binding.textLevel.setText("5")}
            in 200..299 -> {Glide.with(this).load(R.drawable.level_200to299).into(binding.profileImage)
                binding.textLevel.setText("6")}
            in 300..499 -> {Glide.with(this).load(R.drawable.level_300to499).into(binding.profileImage)
                binding.textLevel.setText("7")}
            in 500..799 -> {Glide.with(this).load(R.drawable.level_500to799).into(binding.profileImage)
                binding.textLevel.setText("8")}
            in 800..1199 -> {Glide.with(this).load(R.drawable.level_800to1199).into(binding.profileImage)
                binding.textLevel.setText("9")}
            in 1200..1699 -> {Glide.with(this).load(R.drawable.level_1200to1699).into(binding.profileImage)
                binding.textLevel.setText("10")}
            in 1700..2299 -> {Glide.with(this).load(R.drawable.level_1700to2299).into(binding.profileImage)
                binding.textLevel.setText("11")}
            else -> {Glide.with(this).load(R.drawable.level_3000).into(binding.profileImage)
                binding.textLevel.setText("GOD")}
        }


        // Modifica del nome utente nel fragment profile

        binding.MyName.setOnKeyListener(View.OnKeyListener{v,keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){

                var NickName: String = binding.MyName.getText().toString().trim()

                pref.edit().putString("NICKNAME", NickName).apply()

                SavedName = pref.getString("NICKNAME", "")
                binding.MyName.setText(SavedName)

                Toast.makeText(this.requireActivity(), "Name saved", Toast.LENGTH_SHORT).show()
                return@OnKeyListener true
            }
            false
        })



        binding.infoButton.setOnClickListener{
            val  dialogBinding = layoutInflater.inflate(R.layout.profile_dialog, null)

            val myDialog = Dialog(requireContext())
            myDialog.setContentView(dialogBinding)

            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.show()
        }

        binding.textCredits.setOnClickListener{
            val  dialogBinding = layoutInflater.inflate(R.layout.credits_dialog, null)

            val myDialog = Dialog(requireContext())
            myDialog.setContentView(dialogBinding)

            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.show()
        }

        return view
    }


}
package com.example.organizd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment

import com.example.organizd.databinding.ActivityMainBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.material.bottomnavigation.BottomNavigationView



class MainActivity : AppCompatActivity(), Player.Listener {






    lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        val calendarFragment = CalendarFragment()
        val timerFragment = TimerFragment()
        val profileFragment = ProfileFragment()




        setCurrentFragment(homeFragment)

        bottomNav = findViewById(R.id.bottomNavigationView) as BottomNavigationView
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.myHome ->setCurrentFragment(homeFragment)
                R.id.myCalendar -> setCurrentFragment(calendarFragment)
                R.id.myTimer -> setCurrentFragment(timerFragment)
                R.id.myProfile -> setCurrentFragment(profileFragment)
            }
            true
        }




    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }



}


package com.example.organizd

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.organizd.databinding.FragmentTimerBinding
import java.util.*
import kotlin.collections.ArrayList


@Suppress("DEPRECATION")
class TimerFragment : Fragment(R.layout.fragment_timer) {

    lateinit var binding: FragmentTimerBinding
    lateinit var dataHelper: DataHelper

    lateinit var mediaplayer: MediaPlayer

    var songs: ArrayList<Int> = ArrayList()

    var currentIndex = 0

    lateinit var runnable: Runnable
    private var handler = Handler()

    private val timer = Timer()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.fragment_timer, container, false)

        val mNotificationManager = activity!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        binding = FragmentTimerBinding.inflate(layoutInflater)
        val view = binding.root

        dataHelper = DataHelper(requireActivity()) // Non posso inserire nelle parentesi (applicationContext) poiché mi trovo in un fragment.


        binding.startButton.setOnClickListener{ startStopAction() }
        binding.resetButton.setOnClickListener{ resetAction() }

        if(dataHelper.timerCounting())
        {
            startTimer()
        }
        else
        {
            stopTimer()
            if(dataHelper.startTime() != null && dataHelper.stopTime() != null)
            {
                val time = Date().time - calcRestartTime().time
                binding.timeTV.text = timeStringFromLong(time)
            }
        }


        // Aggiornamento costante della vista
        timer.scheduleAtFixedRate(TimeTask(), 0, 500) // Ritardo nell'aggiornamento pari a zero e controlleremo ogni mezzo secondo.


        // Info toast
        binding.infoIcon.setOnClickListener{
            Toast.makeText(this@TimerFragment.requireActivity(), "La funzione total focus permette di silenziare qualunque notifica per migliorare la tua concentrazione.", Toast.LENGTH_LONG).show()
        }


        
        // Prova attivazione modalità non disturbare.

        binding.switchNotDisturb.setOnCheckedChangeListener{ buttonView, isChecked ->

            if(isChecked)
            {
                if (!mNotificationManager.isNotificationPolicyAccessGranted) {
                    val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
                    startActivity(intent)
                }
            }

        }

        // Richiamo funzione player
        musicPlayer()




        return view
    }









    override fun onViewCreated(view: View , savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

    }


    // Music player
    private fun musicPlayer()
    {

        nameAndCoverSong()


        songs.add(0, R.raw.jazz_music)
        songs.add(1, R.raw.lofi_hiphop)
        songs.add(2,R.raw.relaxing_classical_music)



        // Verifico subito se la variabile mediaplayer non è già stata inizializzata
        if(!this::mediaplayer.isInitialized){
            mediaplayer = MediaPlayer.create(this@TimerFragment.requireActivity(), songs.get(currentIndex))
        }


        // seekbar in partenza a 0
        binding.seekBar.progress = 0


        // Impostiamo il valore massimo della seekbar pari alla durata del brano
        binding.seekBar.max = mediaplayer.duration


        if(mediaplayer.isPlaying)
        {
            binding.btnPlay.setImageResource(R.drawable.ic_pause)
        }

        binding.btnPlay.setOnClickListener{
            // Andiamo a verificare se il player è partito
            if(!mediaplayer.isPlaying)
            {
                mediaplayer.start()
                // Se è partito andiamo a cambiare l'immagine del bottone play con quella si pausa
                binding.btnPlay.setImageResource(R.drawable.ic_pause)
            }
            else // Altrimenti il player è in pausa
            {
                mediaplayer.pause()
                // Essendo in pausa il bottone diventa play
                binding.btnPlay.setImageResource(R.drawable.ic_play)
            }

        }

        // Ora facciamo in modo che quando viene spostata la seekbar dovrà spostarsi anche il brano
        binding.seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, pos: Int, changed: Boolean) {
                // Quando cambia la prograssione della barra cambia la progressione del brano
                if(changed)
                {
                    mediaplayer.seekTo(pos)

                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })


        runnable = Runnable {
            binding.seekBar.progress = mediaplayer.currentPosition
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)

        // Facciamo in modo che quando il brano finisce la seekbar torni a 0
        mediaplayer.setOnCompletionListener {
            binding.btnPlay.setImageResource(R.drawable.ic_play)
            binding.seekBar.progress = 0
        }


        // Funzionamento bottone next
        binding.btnNext.setOnClickListener{
            if(mediaplayer.isPlaying)
            {
                binding.btnPlay.setImageResource(R.drawable.ic_pause)
            }
            else
            {
                binding.btnPlay.setImageResource(R.drawable.ic_pause)
            }

            if(currentIndex < songs.size - 1)
            {
                currentIndex++
            }
            else
            {
                currentIndex = 0
            }

            if(mediaplayer.isPlaying)
            {
                mediaplayer.stop()
            }

            mediaplayer = MediaPlayer.create(this@TimerFragment.requireActivity(), songs.get(currentIndex))
            mediaplayer.start()
            nameAndCoverSong()
        }


        // Funzionamento bottone prev
        binding.btnPrev.setOnClickListener{
            if(mediaplayer.isPlaying)
            {
                binding.btnPlay.setImageResource(R.drawable.ic_pause)
            }
            else
            {
                binding.btnPlay.setImageResource(R.drawable.ic_pause)
            }

            if(currentIndex > 0)
            {
                currentIndex--
            }
            else
            {
                currentIndex = songs.size - 1
            }

            if(mediaplayer.isPlaying)
            {
                mediaplayer.stop()
            }

            mediaplayer = MediaPlayer.create(this@TimerFragment.requireActivity(), songs.get(currentIndex))
            mediaplayer.start()
            nameAndCoverSong()
        }

    }


    // Funzione per l'assegnazione del nome e della cover del brano riprodotto
    private fun nameAndCoverSong(){
        if(currentIndex == 0)
        {
            binding.textMusicName.setText("Jazz music")
            binding.coverImage.setImageResource(R.drawable.jazz_music_cover)
        }
        if(currentIndex == 1)
        {
            binding.textMusicName.setText("LoFi HipHop music")
            binding.coverImage.setImageResource(R.drawable.lofi_cover)
        }
        if(currentIndex == 2)
        {
            binding.textMusicName.setText("Classic music")
            binding.coverImage.setImageResource(R.drawable.classical_music_cover)
        }
    }




    private inner class TimeTask: TimerTask()
    {
        override fun run()
        {
            //println(LocalDateTime.now())
            if(dataHelper.timerCounting()) // Se il timer è partito
            {
                //val tempo: DataHelper(context)
                val time = Date().time - dataHelper.startTime()!!.time // Allora per sapere dove passa calcolo l'ora attuale (Date().time) meno l'ora d'inizio (dataHelpre.startTime()!!.time)
                binding.timeTV.text = timeStringFromLong(time)
            }
        }
    }

    private fun resetAction() {

        dataHelper.setStopTime(null)
        dataHelper.setStartTime(null)
        stopTimer()
        binding.timeTV.text = timeStringFromLong(0)

    }

    private fun stopTimer()
    {
        dataHelper.setTimerCounting(false)
        binding.startButton.text = getString(R.string.start)
    }

    private fun startTimer()
    {
        dataHelper.setTimerCounting(true)
        binding.startButton.text = getString(R.string.stop)
    }

    private fun startStopAction()
    {
        if(dataHelper.timerCounting())
        {
            dataHelper.setStopTime(Date())
            stopTimer()
        }
        else
        {
            if(dataHelper.stopTime() != null)
            {
                dataHelper.setStartTime(calcRestartTime())
                dataHelper.setStopTime(null)
            }
            else
            {
                dataHelper.setStartTime(Date())
            }
            startTimer()
        }
    }

    private fun calcRestartTime(): Date
    {
        val diff = dataHelper.startTime()!!.time - dataHelper.stopTime()!!.time
        return Date(System.currentTimeMillis() + diff) // (Ora di inizio - ora di fine) aggiugilo all'ora corrente.
    }

    private fun timeStringFromLong(ms: Long): String
    {

        val seconds = (ms / 1000) % 60
        val minutes = (ms / (1000 * 60) % 60)
        val hours = (ms / (1000 * 60 * 60) % 24)
        return makeTimeString(hours, minutes, seconds)

    }

    private fun makeTimeString(hours: Long, minutes: Long, seconds: Long): String
    {

        return String.format("%02d:%02d:%02d", hours, minutes, seconds)

    }

}



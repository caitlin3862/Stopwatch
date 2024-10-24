package com.example.stopwatch

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var playButton: ImageButton
    private lateinit var pauseButton: ImageButton
    private lateinit var resetButton: ImageButton
    private lateinit var timeDisplay: TextView

    private val handler: Handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable = Runnable { }

    private var displayTime = ""
    private var tenths: Int = 0
    private var seconds: Int = 0
    private var minutes: Int = 0
    private var paused = true;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playButton = findViewById(R.id.play)
        pauseButton = findViewById(R.id.pause)
        resetButton = findViewById(R.id.reset)
        timeDisplay = findViewById(R.id.timeDisplayed)

        playButton.setOnClickListener{
            start()
            paused = false;
            handler.postDelayed(runnable, 100)
        }

        pauseButton.setOnClickListener{
            pause()
            paused = true
        }

        resetButton.setOnClickListener {
            reset()
        }
    }

    private fun start(){
        if (paused) {
            runnable = Runnable {
                tenths++;
                if (tenths > 9) {
                    tenths = 0
                    seconds++
                }
                if (seconds > 59) {
                    seconds = 0;
                    minutes++
                }
                displayTime()
                handler.postDelayed(runnable, 100)
            }
        }
    }

    private fun pause(){
        if (!paused){
            handler.removeCallbacks(runnable)
        }
    }

    private fun reset(){
        tenths = 0
        seconds = 0
        minutes = 0
        displayTime()
    }

    private fun displayTime(){
        displayTime=""
        if (minutes < 10){
            displayTime += "0" + minutes.toString() + ":"
        } else {
            displayTime += minutes.toString() + ":"
        }
        if (seconds < 10){
            displayTime += "0" + seconds.toString()
        } else {
            displayTime += seconds.toString()
        }
        displayTime += "." + tenths.toString()
        timeDisplay.text = displayTime;
    }
}
package com.github.jw3.tl

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.github.jw3.tl.db.Load
import com.github.jw3.tl.db.Repository
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource
import com.google.android.exoplayer2.ui.StyledPlayerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var db: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<StyledPlayerView>(R.id.camView)?.let {
            val user = "admin"
            val pass = "password123"
            val host = "192.168.1.72"
            val minBuffMs = 250
            val maxBuffMs = 500
            val playBuffMs = 250
            val rebuffMs = 250

            println("creating player ==== $user $pass $host")

            val uri = Uri.parse("rtsp://$user:$pass@$host/cam/realmonitor?channel=1&subtype=0")

            val f = RtspMediaSource.Factory()
            //f.setDebugLoggingEnabled(true)

            val lc = DefaultLoadControl.Builder()
                .setBufferDurationsMs(minBuffMs, maxBuffMs, playBuffMs, rebuffMs)
                .build()

            val source = f.createMediaSource(MediaItem.fromUri(uri))
            val player = ExoPlayer.Builder(applicationContext).setLoadControl(lc).build()

            player.setMediaSource(source)
            player.prepare()

            player.playWhenReady = true
            it.player = player
        }

        switch1.setOnClickListener {
            if (switch1.isChecked) startTimer() else stopTimer()
        }

        saveButton.setOnLongClickListener {
            db.insertLoad(Load("default", timeInSeconds, bushelEstimate, System.currentTimeMillis() / 1000))
            Toast.makeText(applicationContext, "This load has been recorded", Toast.LENGTH_SHORT)
                .show()

            resetUI()

            db.allLoads().forEach { load ->
                println(load)
            }

            true
        }

        saveButton.setOnClickListener {
            Toast.makeText(applicationContext, "Press and hold to save", Toast.LENGTH_SHORT).show()
        }
    }

    private var timeInSeconds = 0
    private var bushelEstimate = 0.0
    private var mHandler: Handler? = null

    private fun startTimer() {
        mHandler = Handler(Looper.getMainLooper())
        mStatusChecker.run()

        saveButton.isEnabled = false
    }

    private fun stopTimer() {
        mHandler?.removeCallbacks(mStatusChecker)

        saveButton.isVisible = true
        saveButton.isEnabled = true
    }

    private var mStatusChecker: Runnable = object : Runnable {
        override fun run() {
            try {
                timeInSeconds += 1
                updateCounters()
            } finally {
                mHandler!!.postDelayed(this, 1000)
            }
        }
    }

    private fun resetUI() {
        timeInSeconds = 0
        updateCounters()
        saveButton.isVisible = false
    }

    private fun updateCounters() {
        bushelEstimate = timeInSeconds * .47
        timer.text = String.format("%d:%02d", timeInSeconds / 60, timeInSeconds % 60)
        bushelCounter.text = String.format("%.1f", bushelEstimate)
    }

    var delay = 5

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        println(event)
        when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_UP -> {
                event?.let {
                    if (event.repeatCount > delay && event.repeatCount % delay == 0) {
                        slider.value = Math.min(100f, slider.value + slider.stepSize)
                    }
                }
                return true
            }
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                println(".")
                slider.value = Math.max(0f, slider.value - slider.stepSize)
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}

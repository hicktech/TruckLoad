package com.github.jw3.tl.ui

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.jw3.tl.R
import com.github.jw3.tl.db.Load
import com.github.jw3.tl.db.Repository
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource
import com.google.android.exoplayer2.ui.StyledPlayerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_load_control.*
import kotlinx.android.synthetic.main.fragment_load_control.view.*
import javax.inject.Inject

@AndroidEntryPoint
class LoadControl : Fragment() {
    @Inject
    lateinit var db: Repository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val f =  inflater.inflate(R.layout.fragment_load_control, container, false)

        f.findViewById<StyledPlayerView>(R.id.camView)?.let {
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
            val player = ExoPlayer.Builder(it.context).setLoadControl(lc).build()

            player.setMediaSource(source)
            player.prepare()

            player.playWhenReady = true
            it.player = player
        }

        f.switch1.setOnClickListener {
            if (switch1.isChecked) startTimer() else stopTimer()
        }

        f.saveButton.setOnLongClickListener {
            db.insertLoad(Load("default", timeInSeconds, bushelEstimate, System.currentTimeMillis() / 1000))
            Toast.makeText(context, "This load has been recorded", Toast.LENGTH_SHORT)
                .show()

            resetUI()

            db.allLoads().forEach { load ->
                println(load)
            }

            true
        }

        f.saveButton.setOnClickListener {
            Toast.makeText(context, "Press and hold to save", Toast.LENGTH_SHORT).show()
        }

        f.showListButton.setOnClickListener {
            findNavController().navigate(R.id.action_loadControl_to_loadList)
        }



        return f;
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
}

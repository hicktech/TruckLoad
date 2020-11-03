package com.github.jw3.tl

import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.rtsp.RtspDefaultClient
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var player: SimpleExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val user = "admin"
        val pass = "password"
        val host = "192.168.1.1"
        val minBuffMs = 750
        val maxBuffMs = 750
        val playBuffMs = 500
        val rebuffMs = 750

        println("creating player ==== $user $pass $host")

        val uri = Uri.parse("rtsp://$user:$pass@$host/cam/realmonitor?channel=1&subtype=0")

        val lc = DefaultLoadControl.Builder()
            .setBufferDurationsMs(minBuffMs, maxBuffMs, playBuffMs, rebuffMs)
            .createDefaultLoadControl()
        val ts = DefaultTrackSelector(applicationContext)

        val f = RtspMediaSource.Factory(RtspDefaultClient.factory())
            .setIsLive(true)
            .createMediaSource(uri)

        player = ExoPlayerFactory.newSimpleInstance(applicationContext, ts, lc)
        player.prepare(
            f
        )
        player.playWhenReady = true



    }

    var delay = 5


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        println(event)
        when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_UP -> {
                event?.let {
                    if(event.repeatCount > delay && event.repeatCount % delay == 0) {
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
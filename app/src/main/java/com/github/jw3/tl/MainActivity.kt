package com.github.jw3.tl

import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.FixedTrackSelection
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
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

package com.github.jw3.tl

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.rtsp.RtspDefaultClient
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector

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

        player = ExoPlayerFactory.newSimpleInstance(applicationContext, ts, lc)
        player.prepare(
            RtspMediaSource.Factory(RtspDefaultClient.factory())
                .setIsLive(true).createMediaSource(uri)
        )
        player.playWhenReady = true
    }
}
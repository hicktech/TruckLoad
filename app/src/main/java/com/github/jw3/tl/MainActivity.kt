package com.github.jw3.tl

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    val delay = 5
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_UP -> {
                event?.let {
                    if (event.repeatCount > delay && event.repeatCount % delay == 0) {
                        /// todo;; eventbus to fragment?
                        /// slider.value = Math.min(100f, slider.value + slider.stepSize)
                    }
                }
                return true
            }
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                /// todo;; eventbus to fragment?
                /// slider.value = Math.max(0f, slider.value - slider.stepSize)
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}

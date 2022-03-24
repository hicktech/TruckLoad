package com.github.jw3.tl.prefs

interface CamPrefs {
    val host: String
    val user: String
    val pass: CharArray

    companion object {
        @JvmStatic
        fun newInstance(url: String, user: String, pass: CharArray) = object : CamPrefs {
            override val host: String
                get() = url
            override val user: String
                get() = user
            override val pass: CharArray
                get() = pass
        }
    }
}

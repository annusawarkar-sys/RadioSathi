package com.ashutosh.radiosathi

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

class TextSpeaker(context: Context) : TextToSpeech.OnInitListener {
    private var ready = false
    private val tts = TextToSpeech(context, this)

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale("en", "IN")
            tts.setSpeechRate(0.88f)
            ready = true
            speak("Radio Sathi ready. Tap speak command and say play one, list favourites, or help.")
        }
    }

    fun speak(message: String) {
        if (ready) tts.speak(message, TextToSpeech.QUEUE_FLUSH, null, "RADIO_SATHI")
    }

    fun shutdown() {
        tts.stop(); tts.shutdown()
    }
}

package com.ashutosh.radiosathi

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setPadding
import android.app.Activity

class MainActivity : Activity() {
    private lateinit var speaker: TextSpeaker
    private lateinit var radioPlayer: RadioPlayerManager
    private lateinit var voiceManager: VoiceCommandManager
    private lateinit var processor: CommandProcessor
    private lateinit var status: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        speaker = TextSpeaker(this)
        radioPlayer = RadioPlayerManager(this)
        val channels = ChannelRepository.loadChannels(this)
        processor = CommandProcessor(this, channels, radioPlayer, speaker)
        voiceManager = VoiceCommandManager(this, { command -> status.text = "Command: $command"; processor.process(command) }, { msg -> status.text = msg; speaker.speak(msg) })
        buildUi()
    }

    private fun buildUi() {
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(32)
        }
        status = TextView(this).apply { text = "Radio Sathi ready"; textSize = 24f; gravity = Gravity.CENTER }
        val speak = Button(this).apply { text = "Speak Command"; textSize = 28f; setOnClickListener { status.text = "Listening..."; speaker.speak("Listening. Say your command."); voiceManager.ensurePermissionAndStart() } }
        val fav = Button(this).apply { text = "List Favourites"; textSize = 24f; setOnClickListener { processor.process("list favourites") } }
        val help = Button(this).apply { text = "Help"; textSize = 24f; setOnClickListener { processor.process("help") } }
        val stop = Button(this).apply { text = "Stop"; textSize = 24f; setOnClickListener { processor.process("stop") } }
        layout.addView(status); layout.addView(speak); layout.addView(fav); layout.addView(help); layout.addView(stop)
        setContentView(layout)
    }

    override fun onDestroy() {
        super.onDestroy()
        voiceManager.destroy(); radioPlayer.release(); speaker.shutdown()
    }
}

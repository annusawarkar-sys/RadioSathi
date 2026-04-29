package com.ashutosh.radiosathi

import android.app.Activity
import android.content.Intent
import android.net.Uri

class CommandProcessor(
    private val activity: Activity,
    private val channels: List<RadioChannel>,
    private val radioPlayer: RadioPlayerManager,
    private val speaker: TextSpeaker
) {
    private var currentIndex = -1

    fun process(raw: String) {
        val command = normalizeNumberWords(raw.lowercase().trim())
        when {
            command.contains("stop") -> { radioPlayer.stop(); speaker.speak("Stopped") }
            command.contains("pause") -> { radioPlayer.pause(); speaker.speak("Paused") }
            command.contains("resume") || command.contains("play again") -> { radioPlayer.resume(); speaker.speak("Resuming") }
            command.contains("current") || command.contains("what is playing") -> speaker.speak(radioPlayer.currentName())
            command.contains("list") && (command.contains("favourite") || command.contains("favorite")) -> speakFavourites()
            command.contains("help") -> speakHelp()
            command.contains("next") -> playNext()
            command.contains("previous") || command.contains("back") -> playPrevious()
            command.contains("youtube") -> openYouTube(command)
            else -> findMatchingChannel(command)?.let { play(it) }
                ?: speaker.speak("I did not understand. Say help, list favourites, play one, play news, or stop.")
        }
    }

    private fun play(ch: RadioChannel) {
        currentIndex = channels.indexOf(ch)
        speaker.speak("Playing ${ch.name}")
        try { radioPlayer.play(ch) } catch (e: Exception) { speaker.speak("I could not play ${ch.name}. Stream may be unavailable.") }
    }

    private fun playNext() {
        if (channels.isEmpty()) return
        currentIndex = if (currentIndex < channels.size - 1) currentIndex + 1 else 0
        play(channels[currentIndex])
    }

    private fun playPrevious() {
        if (channels.isEmpty()) return
        currentIndex = if (currentIndex > 0) currentIndex - 1 else channels.size - 1
        play(channels[currentIndex])
    }

    private fun speakFavourites() {
        val msg = channels.joinToString(". ") { "${it.code}, ${it.name}" }
        speaker.speak("Favourite channels are. $msg. Say play one, play two, or channel name.")
    }

    private fun speakHelp() {
        speaker.speak("Say play one, play two, list favourites, next, previous, pause, resume, stop, current channel, or play Kishore Kumar on YouTube.")
    }

    private fun findMatchingChannel(command: String): RadioChannel? {
        return channels.firstOrNull { ch ->
            command.contains("play ${ch.code}") || command.contains("channel ${ch.code}") ||
            command.contains("code ${ch.code}") || command.contains("radio ${ch.code}") ||
            command == ch.code.toString() || ch.aliases.any { command.contains(normalizeNumberWords(it.lowercase())) }
        }
    }

    private fun openYouTube(command: String) {
        val query = command.replace("play", "").replace("open", "").replace("youtube", "").replace("search", "").replace("for", "").trim()
        val uri = if (query.isBlank()) Uri.parse("https://www.youtube.com") else Uri.parse("https://www.youtube.com/results?search_query=${Uri.encode(query)}")
        speaker.speak(if (query.isBlank()) "Opening YouTube" else "Opening YouTube for $query")
        val ytIntent = Intent(Intent.ACTION_VIEW, uri).apply { setPackage("com.google.android.youtube") }
        try { activity.startActivity(ytIntent) } catch (e: Exception) { activity.startActivity(Intent(Intent.ACTION_VIEW, uri)) }
    }

    private fun normalizeNumberWords(s: String): String = s
        .replace("number one", "1").replace("code one", "1").replace("channel one", "1").replace("radio one", "1").replace("one", "1")
        .replace("number two", "2").replace("code two", "2").replace("channel two", "2").replace("radio two", "2").replace("two", "2")
        .replace("number three", "3").replace("code three", "3").replace("channel three", "3").replace("radio three", "3").replace("three", "3")
        .replace("number four", "4").replace("code four", "4").replace("channel four", "4").replace("radio four", "4").replace("four", "4")
        .replace("number five", "5").replace("code five", "5").replace("channel five", "5").replace("radio five", "5").replace("five", "5")
        .replace("number six", "6").replace("code six", "6").replace("channel six", "6").replace("radio six", "6").replace("six", "6")
        .replace("number seven", "7").replace("code seven", "7").replace("channel seven", "7").replace("radio seven", "7").replace("seven", "7")
        .replace("number eight", "8").replace("code eight", "8").replace("channel eight", "8").replace("radio eight", "8").replace("eight", "8")
        .replace("number nine", "9").replace("code nine", "9").replace("channel nine", "9").replace("radio nine", "9").replace("nine", "9")
        .replace("number ten", "10").replace("code ten", "10").replace("channel ten", "10").replace("radio ten", "10").replace("ten", "10")
}

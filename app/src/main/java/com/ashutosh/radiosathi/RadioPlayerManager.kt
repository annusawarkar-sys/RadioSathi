package com.ashutosh.radiosathi

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

class RadioPlayerManager(context: Context) {
    private val player = ExoPlayer.Builder(context).build()
    private var currentChannel: RadioChannel? = null

    fun play(channel: RadioChannel) {
        currentChannel = channel
        val item = MediaItem.fromUri(channel.streamUrl)
        player.setMediaItem(item)
        player.prepare()
        player.playWhenReady = true
    }

    fun pause() = player.pause()
    fun resume() { player.playWhenReady = true }
    fun stop() = player.stop()
    fun currentName(): String = currentChannel?.name ?: "No channel is playing"
    fun release() = player.release()
}

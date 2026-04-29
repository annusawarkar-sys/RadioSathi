package com.ashutosh.radiosathi

import android.content.Context
import kotlinx.serialization.json.Json

object ChannelRepository {
    fun loadChannels(context: Context): List<RadioChannel> {
        val jsonText = context.assets.open("channels.json").bufferedReader().use { it.readText() }
        return Json { ignoreUnknownKeys = true }.decodeFromString(jsonText)
    }
}

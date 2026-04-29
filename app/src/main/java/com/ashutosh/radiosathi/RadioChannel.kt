package com.ashutosh.radiosathi

import kotlinx.serialization.Serializable

@Serializable
data class RadioChannel(
    val code: Int,
    val name: String,
    val aliases: List<String>,
    val streamUrl: String,
    val category: String,
    val language: String,
    val region: String,
    val favourite: Boolean = true
)

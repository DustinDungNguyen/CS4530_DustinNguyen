// File: ApiFunFact.kt
// Author: Dustin Nguyen — CS 4530
// Description: Data model for deserializing fun facts from the remote API.
package com.example.funfacts.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Matches https://uselessfacts.jsph.pl/random.json?language=en
@Serializable
data class ApiFunFact(
    @SerialName("id") val remoteId: String? = null,
    @SerialName("text") val text: String,
    @SerialName("source") val source: String? = null,
    @SerialName("language") val language: String? = null,
    @SerialName("permalink") val permalink: String? = null
)

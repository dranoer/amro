package com.amro.app.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreResponseDto(
    @SerialName("genres")
    val genres: List<GenreDto> = emptyList()
)

@Serializable
data class GenreDto(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String? = null
)
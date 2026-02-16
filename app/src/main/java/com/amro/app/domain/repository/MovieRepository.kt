package com.amro.app.domain.repository

import com.amro.app.domain.model.Genre
import com.amro.app.domain.model.Movie
import com.amro.app.domain.model.Detail

interface MovieRepository {
    suspend fun getMovies(): List<Movie>

    suspend fun getDetail(id: Int): Detail

    suspend fun getGenres(): List<Genre>
}
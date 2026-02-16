package com.amro.app.domain.repository

import com.amro.app.core.ApiResult
import com.amro.app.domain.model.Genre
import com.amro.app.domain.model.Movie
import com.amro.app.domain.model.Detail

interface MovieRepository {
    suspend fun getMovies(): ApiResult<List<Movie>>

    suspend fun getDetail(id: Int): ApiResult<Detail>

    suspend fun getGenres(): ApiResult<List<Genre>>
}
package com.amro.app.domain.usecase

import com.amro.app.domain.model.Detail
import com.amro.app.domain.model.Movie
import com.amro.app.domain.repository.MovieRepository
import javax.inject.Inject

class MovieUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend fun getTrendingMovies(): List<Movie> {
        return repository.getMovies()
    }

    suspend fun getMovieDetail(id: Int): Detail {
        return repository.getDetail(id)
    }
}
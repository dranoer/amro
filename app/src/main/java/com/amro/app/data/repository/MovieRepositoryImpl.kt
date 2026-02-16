package com.amro.app.data.repository

import com.amro.app.data.mapper.toDomain
import com.amro.app.data.remote.ApiService
import com.amro.app.domain.model.Genre
import com.amro.app.domain.model.Movie
import com.amro.app.domain.model.Detail
import com.amro.app.domain.repository.MovieRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class MovieRepositoryImpl(private val api: ApiService) : MovieRepository {

    private var cachedGenres: List<Genre>? = null

    override suspend fun getMovies(): List<Movie> = coroutineScope {
        val genres = getGenres()
        val deferredMovies = (1..5).map { page ->
            async { api.trendingMoviesWeek(page).results }
        }

        val allMovies = deferredMovies.awaitAll().flatten().map { movieDto ->
            movieDto.toDomain().copy(
                genres = genres.filter { it.id in movieDto.genreIds }
            )
        }

        allMovies.distinctBy { it.id }
    }

    override suspend fun getDetail(id: Int): Detail {
        return api.movieDetail(id).toDomain()
    }

    override suspend fun getGenres(): List<Genre> {
        return cachedGenres ?: api.genres().genres.map { it.toDomain() }.also {
            cachedGenres = it
        }
    }
}

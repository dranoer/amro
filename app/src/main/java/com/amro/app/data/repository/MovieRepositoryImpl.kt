package com.amro.app.data.repository

import com.amro.app.core.ApiResult
import com.amro.app.core.toErrorType
import com.amro.app.data.mapper.toDomain
import com.amro.app.data.remote.ApiService
import com.amro.app.domain.model.Detail
import com.amro.app.domain.model.Genre
import com.amro.app.domain.model.Movie
import com.amro.app.domain.repository.MovieRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class MovieRepositoryImpl(private val api: ApiService) : MovieRepository {

    private var cachedGenres: List<Genre>? = null

    override suspend fun getMovies(): ApiResult<List<Movie>> = coroutineScope {
        val genresResult = getGenres()
        if (genresResult is ApiResult.Error) return@coroutineScope genresResult
        val genres = (genresResult as ApiResult.Success).data

        try {
            val deferredPages = (1..5).map { page ->
                async { api.trendingMoviesWeek(page).results }
            }

            val allMovies = deferredPages.awaitAll().flatten().map { movieDto ->
                movieDto.toDomain().copy(
                    genres = genres.filter { it.id in movieDto.genreIds }
                )
            }

            ApiResult.Success(allMovies.distinctBy { it.id })
        } catch (t: Throwable) {
            ApiResult.Error(t.toErrorType())
        }
    }

    override suspend fun getDetail(id: Int): ApiResult<Detail> = try {
        ApiResult.Success(api.movieDetail(id).toDomain())
    } catch (t: Throwable) {
        ApiResult.Error(t.toErrorType())
    }

    override suspend fun getGenres(): ApiResult<List<Genre>> {
        cachedGenres?.let { return ApiResult.Success(it) }
        
        return try {
            val genres = api.genres().genres.map { it.toDomain() }
            cachedGenres = genres
            ApiResult.Success(genres)
        } catch (t: Throwable) {
            ApiResult.Error(t.toErrorType())
        }
    }
}
package com.amro.app.data.repository

import com.amro.app.data.mapper.toDomain
import com.amro.app.data.remote.ApiService
import com.amro.app.domain.model.Genre
import com.amro.app.domain.model.Movie
import com.amro.app.domain.model.Detail
import com.amro.app.domain.repository.MovieRepository

class MovieRepositoryImpl(private val api: ApiService) : MovieRepository {

    override suspend fun getMovies(): List<Movie> {
        val moviesDto = api.trendingMoviesWeek().results
        val genres = getGenres()

        return moviesDto.map { movieDto ->
            movieDto.toDomain().copy(
                genres = genres.filter { it.id in movieDto.genreIds }
            )
        }
    }

    override suspend fun getDetail(id: Int): Detail {
        return api.movieDetail(id).toDomain()
    }

    override suspend fun getGenres(): List<Genre> {
        return api.genres().genres.map { it.toDomain() }
    }
}

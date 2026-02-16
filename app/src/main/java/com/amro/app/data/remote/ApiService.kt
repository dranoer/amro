package com.amro.app.data.remote

import com.amro.app.data.remote.model.GenreResponseDto
import com.amro.app.data.remote.model.MovieResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("trending/movie/week")
    suspend fun trendingMoviesWeek(
        @Query("page") page: Int = 1
    ): MovieResponseDto

    @GET("genre/movie/list")
    suspend fun genres(): GenreResponseDto
}
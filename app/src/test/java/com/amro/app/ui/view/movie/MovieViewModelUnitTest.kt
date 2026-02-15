package com.amro.app.ui.view.movie

import com.amro.app.domain.model.Movie
import com.amro.app.domain.repository.FakeMoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieViewModelUnitTest {

    private val repository = FakeMoviesRepository()
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given loading state initially when viewmodel created then state is Loading`() = runTest {
        // when
        val viewmodel = MovieViewModelconstructor(repository)

        // then
        val state = viewmodel.uiState.value
        assertTrue(state is MovieUiState.Loading)
    }

    @Test
    fun `given repository returns data when loading then state is Success`() = runTest {
        // given
        repository.movies = listOf(
            Movie(
                id = 1,
                title = "Movie A",
                posterUrl = "",
                genreIds = listOf(35),
                popularity = 10.0,
                releaseDate = "2020-01-01"
            ),
            Movie(
                id = 2,
                title = "Movie B",
                posterUrl = "",
                genreIds = listOf(12),
                popularity = 20.0,
                releaseDate = "2021-01-01"
            ),
            Movie(
                id = 3,
                title = "Movie C",
                posterUrl = "",
                genreIds = listOf(35, 12),
                popularity = 15.0,
                releaseDate = "2019-01-01"
            ),
        )

        // when
        val viewModel = MovieViewModelconstructor(repository)
        advanceUntilIdle()

        // then
        val state = viewModel.uiState.value
        assertTrue(state is MovieUiState.Success)
    }

    @Test
    fun `given repository throws exception when loading then state is Error`() = runTest {
        // given
        repository.shouldThrow = true

        // when
        val viewmodel = MovieViewModelconstructor(repository)
        advanceUntilIdle()

        // then
        val state = viewmodel.uiState.value
        assertTrue(state is MovieUiState.Error)
    }

    @Test
    fun `given multiple movies loads when repository changes then state reflects the latest data`() =
        runTest {
            // given first load -> Success (1 item)
            repository.movies = listOf(
                Movie(
                    id = 1,
                    title = "Movie A",
                    posterUrl = "",
                    genreIds = listOf(35),
                    popularity = 10.0,
                    releaseDate = "2020-01-01"
                ),
            )
            val viewModel = MovieViewModelconstructor(repository)
            advanceUntilIdle()
            val firstState = viewModel.uiState.value
            assertTrue(firstState is MovieUiState.Success)

            // when second load -> Success (empty)
            repository.movies = emptyList()
            viewModel.getMovies()
            advanceUntilIdle()

            // then
            val secondState = viewModel.uiState.value
            assertTrue(secondState is MovieUiState.Success)
            assertEquals(emptyList<Movie>(), (secondState as MovieUiState.Success).items)
        }
}
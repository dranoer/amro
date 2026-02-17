package com.amro.app.ui.view.detail

import com.amro.app.domain.model.Detail
import com.amro.app.domain.model.Genre
import com.amro.app.domain.repository.MockMovieRepository
import com.amro.app.domain.usecase.MovieUseCase
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
class DetailViewModelUnitTest {

    private val repository = MockMovieRepository()
    private val useCase = MovieUseCase(repository)
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given repository success when getDetail called then state is Success`() = runTest {
        // given
        repository.detail = Detail(
            id = 1,
            title = "Movie A",
            tagline = "Tagline",
            imageUrl = "",
            genres = listOf(Genre(35, "Comedy")),
            description = "Description",
            voteAverage = 8.1,
            voteCount = 100,
            budget = 1_000_000,
            revenue = 10_000_000,
            status = "Released",
            imdbId = "tt123",
            runtime = 120,
            releaseDate = "2020-01-01"
        )
        val viewModel = DetailViewModel(useCase)

        // when
        viewModel.getDetail(1)
        advanceUntilIdle()

        // then
        val state = viewModel.uiState.value
        assertTrue(state is DetailUiState.Success)

        val selectedMovie = (state as DetailUiState.Success).item
        assertEquals("Movie A", selectedMovie.title)
        assertEquals("Description", selectedMovie.description)
    }

    @Test
    fun `given repository error when getDetail called then state is Error`() =
        runTest {
            // given
            repository.shouldThrowInvalidError = true

            // when
            val viewModel = DetailViewModel(useCase)
            viewModel.getDetail(1)
            advanceUntilIdle()

            // then
            val state = viewModel.uiState.value
            assertTrue(state is DetailUiState.Error)
            assertEquals("Something went wrong.", (state as DetailUiState.Error).message)
        }
}
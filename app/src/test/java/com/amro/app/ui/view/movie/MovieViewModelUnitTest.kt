package com.amro.app.ui.view.movie

import com.amro.app.domain.repository.MockMovieRepository
import com.amro.app.domain.usecase.MovieUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieViewModelUnitTest {

    private val repository = MockMovieRepository()
    private val useCase = MovieUseCase(repository)
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
        val viewModel = MovieViewModel(useCase)

        // then
        assertTrue(viewModel.uiState.value is MovieUiState.Loading)
    }

    @Test
    fun `given repository returns data when loading then state is Success`() = runTest {
        // given
        val viewModel = MovieViewModel(useCase)
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect {}
        }

        // when
        advanceUntilIdle()

        // then
        val state = viewModel.uiState.value
        assertTrue(state is MovieUiState.Success)
        val success = state as MovieUiState.Success
        assertEquals(3, success.items.size)
        assertEquals(2, success.genres.size)
    }

    @Test
    fun `given repository throws network exception when loading then state is Error`() = runTest {
        // given
        repository.shouldThrowNetworkError = true
        val viewModel = MovieViewModel(useCase)
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect {}
        }

        // when
        advanceUntilIdle()

        // then
        val state = viewModel.uiState.value
        assertTrue(state is MovieUiState.Error)
        assertEquals("No internet connection.", (state as MovieUiState.Error).message)
    }

    @Test
    fun `given repository throws invalid exception when loading then state is Error`() = runTest {
        // given
        repository.shouldThrowInvalidError = true
        val viewModel = MovieViewModel(useCase)
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect {}
        }

        // when
        advanceUntilIdle()

        // then
        val state = viewModel.uiState.value
        assertTrue(state is MovieUiState.Error)
        assertEquals("Something went wrong.", (state as MovieUiState.Error).message)
    }

    @Test
    fun `given a genre selected when viewmodel onGenreSelected called twice then filter is cleared`() = runTest {
        // given
        val viewModel = MovieViewModel(useCase)
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect {}
        }
        advanceUntilIdle()
        val successState = viewModel.uiState.value as MovieUiState.Success
        val genre = successState.genres.first()
        
        // when
        viewModel.onGenreSelected(genre)
        advanceUntilIdle()
        viewModel.onGenreSelected(genre) // Deselect
        advanceUntilIdle()
        
        // then
        val state = viewModel.uiState.value as MovieUiState.Success
        assertEquals(3, state.items.size)
        assertNull(state.selectedGenre)
    }

    @Test
    fun `given Title ASC sort order when viewmodel onSortOrderChanged called then list is sorted`() = runTest {
        // given
        val viewModel = MovieViewModel(useCase)
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect {}
        }
        advanceUntilIdle()

        // when
        viewModel.onSortOrderChanged(SortOrder.TITLE_ASC)
        advanceUntilIdle()
        
        // then
        val state = viewModel.uiState.value as MovieUiState.Success
        val items = state.items
        assertEquals("Movie A", items[0].title)
        assertEquals("Movie B", items[1].title)
        assertEquals("Movie C", items[2].title)
    }

    @Test
    fun `given Popularity Desc sort order when viewmodel onSortOrderChanged called then list is sorted`() = runTest {
        // given
        val viewModel = MovieViewModel(useCase)
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect {}
        }
        advanceUntilIdle()

        // when
        viewModel.onSortOrderChanged(SortOrder.POPULARITY_DESC)
        advanceUntilIdle()
        
        // then
        val state = viewModel.uiState.value as MovieUiState.Success
        val items = state.items
        assertEquals("Movie B", items[0].title) // 20.0
        assertEquals("Movie C", items[1].title) // 15.0
        assertEquals("Movie A", items[2].title) // 10.0
    }
}

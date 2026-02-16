package com.amro.app.ui.view.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amro.app.domain.usecase.MovieUseCase
import com.amro.app.ui.mapper.MovieUiMapper.toUiGenres
import com.amro.app.ui.mapper.MovieUiMapper.toUiMovies
import com.amro.app.ui.model.GenreModel
import com.amro.app.ui.model.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MovieViewModel @Inject constructor(val useCase: MovieUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieUiState>(MovieUiState.Loading)
    val uiState: StateFlow<MovieUiState> = _uiState.asStateFlow()

    private var allMovies: List<MovieModel> = emptyList()
    private var allGenres: List<GenreModel> = emptyList()

    init {
        getMovies()
    }

    internal fun getMovies() {
        viewModelScope.launch {
            _uiState.value = MovieUiState.Loading
            try {
                allGenres = useCase.getGenres().toUiGenres()
                allMovies = useCase.getTrendingMovies().toUiMovies()

                updateState(selectedGenre = null)
            } catch (e: Exception) {
                _uiState.value = MovieUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    internal fun onGenreSelected(genre: GenreModel) {
        val currentState = _uiState.value as? MovieUiState.Success ?: return
        val nextGenre = if (currentState.selectedGenre == genre) null else genre
        updateState(nextGenre)
    }

    private fun updateState(selectedGenre: GenreModel?) {
        val filtered = if (selectedGenre == null) {
            allMovies
        } else {
            allMovies.filter { movie ->
                movie.genres.any { it.id == selectedGenre.id }
            }
        }

        _uiState.value = MovieUiState.Success(
            items = filtered,
            genres = allGenres,
            selectedGenre = selectedGenre
        )
    }
}
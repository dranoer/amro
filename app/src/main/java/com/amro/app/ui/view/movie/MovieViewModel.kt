package com.amro.app.ui.view.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amro.app.core.ApiResult
import com.amro.app.core.toUserMessage
import com.amro.app.domain.usecase.MovieUseCase
import com.amro.app.ui.mapper.MovieUiMapper.toUiGenres
import com.amro.app.ui.mapper.MovieUiMapper.toUiMovies
import com.amro.app.ui.model.GenreModel
import com.amro.app.ui.model.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MovieViewModel @Inject constructor(private val useCase: MovieUseCase) : ViewModel() {

    private val _allMovies = MutableStateFlow<List<MovieModel>>(emptyList())
    private val _allGenres = MutableStateFlow<List<GenreModel>>(emptyList())
    private val _selectedGenre = MutableStateFlow<GenreModel?>(null)
    private val _sortOrder = MutableStateFlow(SortOrder.POPULARITY_DESC)
    private val _isLoading = MutableStateFlow(true)
    private val _error = MutableStateFlow<String?>(null)

    val uiState: StateFlow<MovieUiState> = combine(
        combine(_isLoading, _error) { l, e -> l to e },
        _allMovies,
        _allGenres,
        _selectedGenre,
        _sortOrder
    ) { (loading, error), movies, genres, selected, sortOrder ->
        when {
            loading -> MovieUiState.Loading
            error != null -> MovieUiState.Error(error)
            else -> {
                var processed = if (selected == null) {
                    movies
                } else {
                    movies.filter { movie -> movie.genres.any { it.id == selected.id } }
                }

                processed = when (sortOrder) {
                    SortOrder.POPULARITY_DESC -> processed.sortedByDescending { it.popularity }
                    SortOrder.POPULARITY_ASC -> processed.sortedBy { it.popularity }
                    SortOrder.TITLE_ASC -> processed.sortedBy { it.title }
                    SortOrder.TITLE_DESC -> processed.sortedByDescending { it.title }
                    SortOrder.RELEASE_DATE_DESC -> processed.sortedByDescending { it.releaseDate }
                    SortOrder.RELEASE_DATE_ASC -> processed.sortedBy { it.releaseDate }
                }

                MovieUiState.Success(
                    items = processed,
                    genres = genres,
                    selectedGenre = selected,
                    sortOrder = sortOrder
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MovieUiState.Loading
    )

    init {
        getMovies()
    }

    fun getMovies() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            val genresResult = useCase.getGenres()
            val moviesResult = useCase.getTrendingMovies()

            if (genresResult is ApiResult.Success && moviesResult is ApiResult.Success) {
                _allGenres.value = genresResult.data.toUiGenres()
                _allMovies.value = moviesResult.data.toUiMovies()
                _isLoading.value = false
            } else {
                val err = (genresResult as? ApiResult.Error) ?: (moviesResult as? ApiResult.Error)
                _error.value = err?.type?.toUserMessage() ?: "Unknown error"
                _isLoading.value = false
            }
        }
    }

    fun onGenreSelected(genre: GenreModel) {
        _selectedGenre.value = if (_selectedGenre.value == genre) null else genre
    }

    fun onSortOrderChanged(order: SortOrder) {
        _sortOrder.value = order
    }
}
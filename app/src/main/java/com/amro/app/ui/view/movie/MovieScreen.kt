package com.amro.app.ui.view.movie

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.amro.app.ui.model.GenreModel
import com.amro.app.ui.model.MovieModel
import com.amro.app.ui.theme.AmroTheme
import com.amro.app.ui.view.component.ErrorView
import com.amro.app.ui.view.component.LoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MovieScreen(
    navigateToDetail: (Int) -> Unit,
    viewModel: MovieViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Trending Movies") }
            )
        }
    ) { padding ->
        MovieScreenContent(
            state = state,
            onGenreClick = { viewModel.onGenreSelected(it) },
            onRetry = { viewModel.getMovies() },
            navigateToDetail = navigateToDetail,
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
private fun MovieScreenContent(
    state: MovieUiState,
    onGenreClick: (GenreModel) -> Unit,
    onRetry: () -> Unit,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        if (state is MovieUiState.Success) {
            GenreFilterRow(
                genres = state.genres,
                selectedGenre = state.selectedGenre,
                onGenreClick = onGenreClick
            )
        }

        Box(modifier = Modifier.weight(1f)) {
            when (state) {
                is MovieUiState.Loading -> {
                    LoadingView(modifier = Modifier.fillMaxSize())
                }

                is MovieUiState.Error -> {
                    ErrorView(
                        message = state.message,
                        onRetryClick = onRetry,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                is MovieUiState.Success -> {
                    MoviesContent(
                        movies = state.items,
                        onMovieClick = navigateToDetail
                    )
                }
            }
        }
    }
}

@Composable
private fun GenreFilterRow(
    genres: List<GenreModel>,
    selectedGenre: GenreModel?,
    onGenreClick: (GenreModel) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(genres, key = { it.id }) { genre ->
            FilterChip(
                selected = genre == selectedGenre,
                onClick = { onGenreClick(genre) },
                label = { Text(genre.name) },
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}

@Composable
private fun MoviesContent(
    movies: List<MovieModel>,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(movies, key = { it.id }) { movie ->
            MovieItem(
                movie = movie,
                onClick = { onMovieClick(movie.id) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieScreenPreview() {
    AmroTheme {
        MovieScreen(navigateToDetail = {})
    }
}
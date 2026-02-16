package com.amro.app.ui.view.movie

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.amro.app.ui.model.MovieModel
import com.amro.app.ui.theme.AmroTheme
import com.amro.app.ui.view.component.ErrorView
import com.amro.app.ui.view.component.LoadingView

@Composable
internal fun MovieScreen(
    navigateToDetail: (Int) -> Unit,
    viewModel: MovieViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold { padding ->
        when (val ui = state) {
            is MovieUiState.Loading -> {
                LoadingView(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                )
            }

            is MovieUiState.Error -> {
                ErrorView(
                    message = ui.message,
                    onRetryClick = { viewModel.getMovies() },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                )
            }

            is MovieUiState.Success -> {
                MoviesContent(
                    movies = ui.items,
                    onMovieClick = navigateToDetail,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                )
            }
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
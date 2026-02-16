package com.amro.app.ui.view.movie

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amro.app.domain.model.Movie
import com.amro.app.ui.theme.AmroTheme

@Composable
internal fun MovieScreen(
    navigateToDetail: (Int) -> Unit
) {
    Scaffold { padding ->
        MoviesContent(
            movies = emptyList(),
            onMovieClick = navigateToDetail,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        )
    }
}

@Composable
private fun MoviesContent(
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        itemsIndexed(movies) { _, movie ->
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
        MovieScreen(
            navigateToDetail = {}
        )
    }
}
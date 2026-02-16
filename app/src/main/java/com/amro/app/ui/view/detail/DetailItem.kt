package com.amro.app.ui.view.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amro.app.domain.model.Genre
import com.amro.app.domain.model.MovieDetail
import com.amro.app.ui.theme.AmroTheme

@Composable
internal fun DetailItem(
    movie: MovieDetail,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = movie.title ?: "")
        Text(text = movie.tagline ?: "")
        Text(text = movie.description ?: "")
        Text(text = movie.genres.joinToString { it.name ?: "" })
        Text(text = "Vote avg: ${movie.voteAverage ?: "-"}")
        Text(text = "Vote count: ${movie.voteCount ?: "-"}")
        Text(text = "Runtime: ${movie.runtime ?: "-"}")
        Text(text = "Release: ${movie.releaseDate ?: "-"}")
        Text(text = "Budget: ${movie.budget ?: "-"}")
        Text(text = "Revenue: ${movie.revenue ?: "-"}")
        Text(text = "Status: ${movie.status ?: "-"}")
        Text(text = "IMDB: ${movie.imdbUrl}")
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailItemPreview() {
    AmroTheme {
        DetailItem(
            movie = MovieDetail(
                id = 1,
                title = "Movie A",
                tagline = "Tagline",
                imageUrl = null,
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
        )
    }
}
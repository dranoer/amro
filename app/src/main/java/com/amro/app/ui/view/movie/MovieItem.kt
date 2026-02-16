package com.amro.app.ui.view.movie

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amro.app.domain.model.Movie
import com.amro.app.ui.theme.AmroTheme

@Composable
internal fun MovieItem(
    movie: Movie,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Text(
            text = movie.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(12.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieItemPreview() {
    AmroTheme {
        MovieItem(
            movie = Movie(
                id = 1,
                title = "Movie A",
                posterUrl = null,
                genreIds = listOf(35),
                popularity = 10.0,
                releaseDate = "2020-01-01"
            ),
            onClick = {}
        )
    }
}
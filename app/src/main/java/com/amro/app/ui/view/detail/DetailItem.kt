package com.amro.app.ui.view.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.amro.app.R
import com.amro.app.ui.model.DetailModel
import com.amro.app.ui.model.GenreModel
import com.amro.app.ui.theme.AmroTheme
import java.text.NumberFormat
import java.util.Locale

@Composable
internal fun DetailContent(
    movie: DetailModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        DetailHeader(movie)

        movie.description?.takeIf { it.isNotBlank() }?.let {
            DetailOverview(it)
        }

        DetailInfoSection(movie)
    }
}

@Composable
private fun DetailHeader(movie: DetailModel) {
    Column {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(movie.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = movie.title,
            placeholder = painterResource(R.drawable.ic_movie_placeholder),
            error = painterResource(R.drawable.ic_movie_placeholder),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = movie.title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        movie.tagline?.takeIf { it.isNotBlank() }?.let {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = it,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
private fun DetailOverview(description: String) {
    Column {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.overview),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = description, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun DetailInfoSection(movie: DetailModel) {
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US)

    Column {
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))

        DetailRow(stringResource(R.string.genres), movie.genresText)
        DetailRow(stringResource(R.string.status), movie.status ?: "-")
        DetailRow(stringResource(R.string.runtime), if (movie.runtime != null) stringResource(R.string.mins, movie.runtime) else "-")
        DetailRow(stringResource(R.string.release_date), movie.releaseDate ?: "-")

        Spacer(modifier = Modifier.height(8.dp))

        DetailRow(stringResource(R.string.vote_average), movie.voteAverage?.toString() ?: "-")
        DetailRow(stringResource(R.string.vote_count), movie.voteCount?.toString() ?: "-")

        Spacer(modifier = Modifier.height(8.dp))

        DetailRow(
            stringResource(R.string.budget),
            if (movie.budget != null && movie.budget > 0) currencyFormatter.format(movie.budget) else "-"
        )
        DetailRow(
            stringResource(R.string.revenue),
            if (movie.revenue != null && movie.revenue > 0) currencyFormatter.format(movie.revenue) else "-"
        )

        Spacer(modifier = Modifier.height(8.dp))

        DetailRow(stringResource(R.string.imdb), movie.imdbUrl ?: "-")
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String
) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailContentPreview() {
    AmroTheme {
        Surface {
            DetailContent(
                movie = DetailModel(
                    id = 1,
                    title = "Movie A",
                    tagline = "Tagline",
                    imageUrl = null,
                    genres = listOf(GenreModel(35, "Comedy"), GenreModel(2, "Drama")),
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
}
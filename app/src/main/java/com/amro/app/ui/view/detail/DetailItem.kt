package com.amro.app.ui.view.detail

import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.amro.app.R
import com.amro.app.ui.model.DetailModel
import com.amro.app.ui.model.GenreModel
import com.amro.app.ui.theme.AmroTheme

@Composable
internal fun DetailItem(item: DetailModel, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        DetailHeader(item)

        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item.description?.takeIf { it.isNotBlank() }?.let {
                DetailOverview(it)
            }
            DetailInfoSection(item)
        }
    }
}

@Composable
private fun DetailHeader(item: DetailModel) {
    var expanded by remember { mutableStateOf(false) }
    val height by animateDpAsState(
        targetValue = if (expanded) 450.dp else 200.dp,
        animationSpec = tween(1000, easing = EaseOutCubic), label = "Height"
    )
    val horizontalPadding by animateDpAsState(
        targetValue = if (expanded) 0.dp else 24.dp,
        animationSpec = tween(1000, easing = EaseOutCubic), label = "Padding"
    )

    LaunchedEffect(Unit) { expanded = true }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            Modifier
                .padding(horizontal = horizontalPadding)
                .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height)
            )
        }

        Column(
            modifier = Modifier.padding(top = 24.dp, start = 24.dp, end = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            item.tagline?.takeIf { it.isNotBlank() }?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
private fun DetailOverview(description: String) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = stringResource(R.string.overview),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Text(text = description, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun DetailInfoSection(
    movie: DetailModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        HorizontalDivider()

        DetailRow(stringResource(R.string.genres), movie.genresText)

        val gridItems = listOf(
            R.string.status to movie.status,
            R.string.runtime to movie.runtime?.let { stringResource(R.string.mins, it) },
            R.string.release_date to movie.releaseDate,
            R.string.vote_average to movie.voteAverage?.toString(),
            R.string.budget to movie.budgetText,
            R.string.revenue to movie.revenueText,
            R.string.vote_count to movie.voteCount?.toString(),
            R.string.imdb to (movie.imdbId ?: "-")
        )

        gridItems.chunked(2).forEach { rowItems ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                rowItems.forEach { (label, value) ->
                    DetailItemRow(
                        label = stringResource(label),
                        value = value ?: "-",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailItemRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailItemPreview() {
    AmroTheme {
        DetailItem(
            DetailModel(
                id = 1, title = "Movie A", tagline = "Tagline", imageUrl = null,
                genres = listOf(GenreModel(35, "Comedy"), GenreModel(18, "Drama")),
                description = "Description", voteAverage = 8.1, voteCount = 100,
                budget = 1_000_000, revenue = 10_000_000, status = "Released",
                imdbId = "tt123", runtime = 120, releaseDate = "2020-01-01"
            )
        )
    }
}
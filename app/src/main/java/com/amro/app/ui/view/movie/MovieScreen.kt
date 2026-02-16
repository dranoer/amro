package com.amro.app.ui.view.movie

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.amro.app.R
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
    val listState = rememberLazyListState()

    LaunchedEffect(
        (state as? MovieUiState.Success)?.selectedGenre,
        (state as? MovieUiState.Success)?.sortOrder
    ) {
        if (state is MovieUiState.Success) {
            listState.scrollToItem(0)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.trending_movies)) },
                actions = {
                    if (state is MovieUiState.Success) {
                        val successState = state as MovieUiState.Success
                        SortMenu(
                            currentOrder = successState.sortOrder,
                            onOrderSelected = viewModel::onSortOrderChanged
                        )
                    }
                }
            )
        }
    ) { padding ->
        MovieScreenContent(
            state = state,
            listState = listState,
            onGenreClick = viewModel::onGenreSelected,
            onRetry = viewModel::getMovies,
            onMovieClick = navigateToDetail,
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
private fun SortMenu(
    currentOrder: SortOrder,
    onOrderSelected: (SortOrder) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(
                painter = painterResource(R.drawable.ic_sort),
                contentDescription = stringResource(R.string.sort)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            SortOrder.entries.forEach { order ->
                DropdownMenuItem(
                    text = { Text(stringResource(order.resId)) },
                    onClick = {
                        onOrderSelected(order)
                        expanded = false
                    },
                    trailingIcon = {
                        if (order == currentOrder) {
                            Icon(
                                painter = painterResource(R.drawable.ic_check),
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun MovieScreenContent(
    state: MovieUiState,
    listState: LazyListState,
    onGenreClick: (GenreModel) -> Unit,
    onRetry: () -> Unit,
    onMovieClick: (Int) -> Unit,
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
                        listState = listState,
                        onMovieClick = onMovieClick
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
    listState: LazyListState,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = listState,
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
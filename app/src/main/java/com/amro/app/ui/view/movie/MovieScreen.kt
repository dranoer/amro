package com.amro.app.ui.view.movie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
    val gridState = rememberLazyGridState()

    LaunchedEffect(
        (state as? MovieUiState.Success)?.selectedGenre,
        (state as? MovieUiState.Success)?.sortOrder
    ) {
        if (state is MovieUiState.Success) gridState.scrollToItem(0)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.trending_movies)) },
                actions = {
                    if (state is MovieUiState.Success) {
                        SortMenu(
                            currentOrder = (state as MovieUiState.Success).sortOrder,
                            onOrderSelected = viewModel::onSortOrderChanged
                        )
                    }
                }
            )
        }
    ) { padding ->
        MovieScreenContent(
            state = state,
            gridState = gridState,
            onGenreClick = viewModel::onGenreSelected,
            onRetry = viewModel::getMovies,
            onMovieClick = navigateToDetail,
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
private fun MovieScreenContent(
    state: MovieUiState,
    gridState: LazyGridState,
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
                is MovieUiState.Loading -> LoadingView(Modifier.fillMaxSize())
                is MovieUiState.Error -> ErrorView(state.message, onRetry, Modifier.fillMaxSize())
                is MovieUiState.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        state = gridState,
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(state.items, key = { it.id }) { movie ->
                            MovieItem(movie = movie, onClick = { onMovieClick(movie.id) })
                        }
                    }
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
                shape = MaterialTheme.shapes.extraLarge,
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier.padding(end = 8.dp)
            )
        }
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
            Icon(painterResource(R.drawable.ic_sort), stringResource(R.string.sort))
        }
        DropdownMenu(expanded, onDismissRequest = { expanded = false }) {
            SortOrder.entries.forEach { order ->
                DropdownMenuItem(
                    text = { Text(stringResource(order.resId)) },
                    onClick = { onOrderSelected(order); expanded = false },
                    trailingIcon = {
                        if (order == currentOrder) Icon(
                            painterResource(R.drawable.ic_check),
                            null
                        )
                    }
                )
            }
        }
    }
}
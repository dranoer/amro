package com.amro.app.ui.view.detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.amro.app.R
import com.amro.app.ui.theme.AmroTheme
import com.amro.app.ui.view.component.ErrorView
import com.amro.app.ui.view.component.LoadingView

@Composable
internal fun DetailScreen(
    id: Int,
    onBackClick: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(id) {
        viewModel.getDetail(id)
    }

    DetailScaffold(
        title = (state as? DetailUiState.Success)?.item?.title ?: "Movie",
        onBackClick = onBackClick
    ) { padding ->
        DetailScreenContent(
            state = state,
            onRetry = { viewModel.getDetail(id) },
            modifier = Modifier.padding(padding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailScaffold(
    title: String,
    onBackClick: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(R.drawable.ic_back),
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        },
        content = content
    )
}

@Composable
private fun DetailScreenContent(
    state: DetailUiState,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (state) {
        is DetailUiState.Loading -> {
            LoadingView(modifier = modifier.fillMaxSize())
        }

        is DetailUiState.Error -> {
            ErrorView(
                message = state.message,
                onRetryClick = onRetry,
                modifier = modifier.fillMaxSize()
            )
        }

        is DetailUiState.Success -> {
            DetailContent(
                movie = state.item,
                modifier = modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailScreenPreview() {
    AmroTheme {
        DetailScreen(
            id = 1,
            onBackClick = {}
        )
    }
}
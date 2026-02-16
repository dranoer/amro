package com.amro.app.ui.view.detail

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.amro.app.R
import com.amro.app.ui.theme.AmroTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DetailScreen(id: Int, onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("title") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(R.drawable.ic_back),
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { padding ->
        DetailItem(
            movie = TODO(),
            modifier = Modifier.padding(padding)
        )
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
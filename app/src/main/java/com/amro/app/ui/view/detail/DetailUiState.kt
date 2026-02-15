package com.amro.app.ui.view.detail

import com.amro.app.domain.model.MovieDetail

sealed class DetailUiState {
    data object Loading : DetailUiState()
    data class Success(val item: MovieDetail) : DetailUiState()
    data class Error(val message: String) : DetailUiState()
}
package com.amro.app.ui.view.detail

import com.amro.app.ui.model.DetailModel

sealed class DetailUiState {
    data object Loading : DetailUiState()
    data class Success(val item: DetailModel) : DetailUiState()
    data class Error(val message: String) : DetailUiState()
}
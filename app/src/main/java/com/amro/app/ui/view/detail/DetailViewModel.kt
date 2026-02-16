package com.amro.app.ui.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amro.app.domain.usecase.MovieUseCase
import com.amro.app.ui.mapper.MovieUiMapper.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
internal class DetailViewModel @Inject constructor(val useCase: MovieUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    internal val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    internal fun getDetail(id: Int) {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            try {
                val result = useCase.getMovieDetail(id)
                _uiState.value = DetailUiState.Success(result.toUi())
            } catch (e: Exception) {
                _uiState.value = DetailUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
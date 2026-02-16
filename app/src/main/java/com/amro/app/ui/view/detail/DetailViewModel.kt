package com.amro.app.ui.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amro.app.core.ApiResult
import com.amro.app.core.toUserMessage
import com.amro.app.domain.usecase.MovieUseCase
import com.amro.app.ui.mapper.MovieUiMapper.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DetailViewModel @Inject constructor(private val useCase: MovieUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    internal val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    internal fun getDetail(id: Int) {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            when (val result = useCase.getMovieDetail(id)) {
                is ApiResult.Success -> {
                    _uiState.value = DetailUiState.Success(result.data.toUi())
                }

                is ApiResult.Error -> {
                    _uiState.value = DetailUiState.Error(result.type.toUserMessage())
                }
            }
        }
    }
}
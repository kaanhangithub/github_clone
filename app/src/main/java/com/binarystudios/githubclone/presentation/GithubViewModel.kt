package com.binarystudios.githubclone.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binarystudios.githubclone.domain.model.Repo
import com.binarystudios.githubclone.domain.model.User
import com.binarystudios.githubclone.domain.usecase.GetRepoListUseCase
import com.binarystudios.githubclone.domain.usecase.GetUserUseCase
import com.binarystudios.githubclone.utils.Constants.FORKS_THRESHOLD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GithubViewModel
@Inject
constructor(
    private val getRepoListUseCase: GetRepoListUseCase,
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {

    var userStateFlow = MutableStateFlow<UIState<User>?>(null)
        private set
    var repoListStateFlow = MutableStateFlow<UIState<List<Repo>>?>(null)
        private set
    var showBadge = MutableStateFlow(false)
        private set

    fun getUserAndRepoList(userId: String) {
        getUser(userId)
        getRepoList(userId)
    }

    private fun getUser(userId: String) {
        getUserUseCase(userId).onEach {
            userStateFlow.value = it
        }.launchIn(viewModelScope)
    }

    private fun getRepoList(userId: String) {
        getRepoListUseCase(userId).onEach { state ->
            when (state) {
                is UIState.Success -> {
                    repoListStateFlow.value = state
                    showBadge.value = state.response.sumOf { it.forks } > FORKS_THRESHOLD
                }

                is UIState.Error,
                UIState.Loading -> repoListStateFlow.value = state
            }
        }.launchIn(viewModelScope)
    }
}
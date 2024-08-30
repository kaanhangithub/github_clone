package com.binarystudios.githubclone.presentation

import com.binarystudios.githubclone.domain.model.Repo
import com.binarystudios.githubclone.domain.model.User

sealed class UIState<out T> {
    data class Success<out T>(val response: T) : UIState<T>()
    data class Error(val error: Throwable) : UIState<Nothing>()
    data object Loading : UIState<Nothing>()
}

data class CombinedUIState(
    val userState: UIState<User>?,
    val repoListState: UIState<List<Repo>>?
)
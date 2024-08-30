package com.binarystudios.githubclone.domain.usecase

import com.binarystudios.githubclone.domain.model.User
import com.binarystudios.githubclone.domain.repository.Repository
import com.binarystudios.githubclone.presentation.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUserUseCase(
    private val repository: Repository
) {
    operator fun invoke(userId: String): Flow<UIState<User>> = flow {
        emit(UIState.Loading)
        try {
            val response = repository.getUser(userId)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(UIState.Success(it))
                }
            } else {
                emit(UIState.Error(IllegalStateException()))
            }
        } catch (e: Exception) {
            emit(UIState.Error(e))
        }
    }
}
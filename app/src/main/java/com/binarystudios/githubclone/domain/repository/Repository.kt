package com.binarystudios.githubclone.domain.repository

import com.binarystudios.githubclone.domain.model.Repo
import com.binarystudios.githubclone.domain.model.User
import retrofit2.Response

interface Repository {
    suspend fun getUser(userId: String): Response<User>
    suspend fun getRepos(userId: String): Response<List<Repo>>
}
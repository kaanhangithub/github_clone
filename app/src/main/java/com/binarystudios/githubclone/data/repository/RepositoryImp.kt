package com.binarystudios.githubclone.data.repository

import com.binarystudios.githubclone.data.remote.GithubApi
import com.binarystudios.githubclone.domain.model.Repo
import com.binarystudios.githubclone.domain.model.User
import com.binarystudios.githubclone.domain.repository.Repository
import retrofit2.Response

class RepositoryImp(
    private val api: GithubApi
) : Repository {
    override suspend fun getUser(userId: String): Response<User> {
        return api.getUser(userId)
    }

    override suspend fun getRepos(userId: String): Response<List<Repo>> {
        return api.getRepos(userId)
    }
}
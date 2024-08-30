package com.binarystudios.githubclone.data.remote

import com.binarystudios.githubclone.domain.model.Repo
import com.binarystudios.githubclone.domain.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {
    @GET("{userId}")
    suspend fun getUser(
        @Path("userId") userId: String
    ): Response<User>

    @GET("{userId}/repos")
    suspend fun getRepos(
        @Path("userId") userId: String
    ): Response<List<Repo>>
}
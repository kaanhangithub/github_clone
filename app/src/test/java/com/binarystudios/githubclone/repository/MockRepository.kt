package com.binarystudios.githubclone.repository

import com.binarystudios.githubclone.domain.model.Repo
import com.binarystudios.githubclone.domain.model.User
import com.binarystudios.githubclone.domain.repository.Repository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import java.net.HttpURLConnection

class MockRepository {
    class FakeSuccessRepo(private val showBadge: Boolean = false) : Repository {
        override suspend fun getUser(userId: String): Response<User> {
            return Response.success(userResponse)
        }

        override suspend fun getRepos(userId: String): Response<List<Repo>> {
            return if (showBadge) Response.success(listOfReposShowBadge) else Response.success(listOfRepos)
        }

        companion object {
            val userResponse = User("mockName", "mockAvatarUrl")
            val listOfRepos =
                listOf(Repo("mockRepoName", "mockRepoDescription", "mockRepoUpdatedAt", 100, 200))
            val listOfReposShowBadge =
                listOf(
                    Repo("mockRepoName", "mockRepoDescription", "mockRepoUpdatedAt", 100, 200),
                    Repo("mockRepoName", "mockRepoDescription", "mockRepoUpdatedAt", 100, 6000)
                )
        }
    }

    class FakeErrorRepo : Repository {
        override suspend fun getUser(userId: String): Response<User> {
            return Response.error(
                HttpURLConnection.HTTP_BAD_REQUEST,
                "".toResponseBody("".toMediaTypeOrNull())
            )
        }

        override suspend fun getRepos(userId: String): Response<List<Repo>> {
            return Response.error(
                HttpURLConnection.HTTP_BAD_REQUEST,
                "".toResponseBody("".toMediaTypeOrNull())
            )
        }
    }
}
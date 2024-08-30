package com.binarystudios.githubclone.di

import com.binarystudios.githubclone.data.remote.GithubApi
import com.binarystudios.githubclone.data.repository.RepositoryImp
import com.binarystudios.githubclone.domain.repository.Repository
import com.binarystudios.githubclone.domain.usecase.GetRepoListUseCase
import com.binarystudios.githubclone.domain.usecase.GetUserUseCase
import com.binarystudios.githubclone.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGithubApi(): GithubApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(api: GithubApi): Repository {
        return RepositoryImp(api)
    }

    @Provides
    @Singleton
    fun provideGetUserUseCase(repository: Repository): GetUserUseCase {
        return GetUserUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetRepoListUseCase(repository: Repository): GetRepoListUseCase {
        return GetRepoListUseCase(repository)
    }
}
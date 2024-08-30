package com.binarystudios.githubclone.usecase

import com.binarystudios.githubclone.CoroutineTestRule
import com.binarystudios.githubclone.domain.model.Repo
import com.binarystudios.githubclone.domain.usecase.GetRepoListUseCase
import com.binarystudios.githubclone.presentation.UIState
import com.binarystudios.githubclone.repository.MockRepository
import com.binarystudios.githubclone.runTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetRepoListUseCaseTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var mockSuccessRepository: MockRepository.FakeSuccessRepo
    private lateinit var mockErrorRepository: MockRepository.FakeErrorRepo
    private lateinit var getRepoListUseCase: GetRepoListUseCase
    private lateinit var getErrorRepoListUseCase: GetRepoListUseCase

    @Before
    fun setUp(){
        mockSuccessRepository = MockRepository.FakeSuccessRepo()
        mockErrorRepository = MockRepository.FakeErrorRepo()
        getRepoListUseCase = GetRepoListUseCase(mockSuccessRepository)
        getErrorRepoListUseCase = GetRepoListUseCase(mockErrorRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test getRepoListUseCase success`() {
        val userId = "mockUserId"
        val expectedResult = MockRepository.FakeSuccessRepo.listOfRepos
        coroutineTestRule.runTest {
            val response = getRepoListUseCase(userId).toList()
            assert(response[0] is UIState.Loading)
            assert(response[1] is UIState.Success<List<Repo>>)
            val actualResult = (response[1] as UIState.Success<List<Repo>>).response
            Assert.assertNotNull(actualResult)
            assertEquals(expectedResult, actualResult)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test getRepoListUseCase error`() {
        val userId = "mockUserId"
        val expectedResult = IllegalStateException()
        coroutineTestRule.runTest {
            val response = getErrorRepoListUseCase(userId).toList()
            assert(response[0] is UIState.Loading)
            assert(response[1] is UIState.Error)
            val actualResult = (response[1] as UIState.Error).error
            Assert.assertNotNull(actualResult)
            assertEquals(expectedResult.javaClass, actualResult.javaClass)
        }
    }
}
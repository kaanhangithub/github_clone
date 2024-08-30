package com.binarystudios.githubclone.usecase

import com.binarystudios.githubclone.CoroutineTestRule
import com.binarystudios.githubclone.domain.model.User
import com.binarystudios.githubclone.domain.usecase.GetUserUseCase
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

class GetUserUseCaseTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var mockSuccessRepository: MockRepository.FakeSuccessRepo
    private lateinit var mockErrorRepository: MockRepository.FakeErrorRepo
    private lateinit var getUserUseCaseTest: GetUserUseCase
    private lateinit var getErrorUserUseCaseTest: GetUserUseCase

    @Before
    fun setUp(){
        mockSuccessRepository = MockRepository.FakeSuccessRepo()
        mockErrorRepository = MockRepository.FakeErrorRepo()
        getUserUseCaseTest = GetUserUseCase(mockSuccessRepository)
        getErrorUserUseCaseTest = GetUserUseCase(mockErrorRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test getUserUseCase success`() {
        val userId = "mockUserId"
        val expectedResult = MockRepository.FakeSuccessRepo.userResponse
        coroutineTestRule.runTest {
            val response = getUserUseCaseTest(userId).toList()
            assert(response[0] is UIState.Loading)
            assert(response[1] is UIState.Success<User>)
            val actualResult = (response[1] as UIState.Success<User>).response
            Assert.assertNotNull(actualResult)
            assertEquals(expectedResult, actualResult)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test getUserUseCase error`() {
        val userId = "mockUserId"
        val expectedResult = IllegalStateException()
        coroutineTestRule.runTest {
            val response = getErrorUserUseCaseTest(userId).toList()
            assert(response[0] is UIState.Loading)
            assert(response[1] is UIState.Error)
            val actualResult = (response[1] as UIState.Error).error
            Assert.assertNotNull(actualResult)
            assertEquals(expectedResult.javaClass, actualResult.javaClass)
        }
    }
}
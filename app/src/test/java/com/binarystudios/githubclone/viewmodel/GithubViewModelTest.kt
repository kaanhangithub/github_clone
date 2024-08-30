package com.binarystudios.githubclone.viewmodel

import com.binarystudios.githubclone.CoroutineTestRule
import com.binarystudios.githubclone.domain.model.Repo
import com.binarystudios.githubclone.domain.model.User
import com.binarystudios.githubclone.domain.usecase.GetRepoListUseCase
import com.binarystudios.githubclone.domain.usecase.GetUserUseCase
import com.binarystudios.githubclone.presentation.GithubViewModel
import com.binarystudios.githubclone.presentation.UIState
import com.binarystudios.githubclone.repository.MockRepository
import com.binarystudios.githubclone.runTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GithubViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var getRepoListUseCase: GetRepoListUseCase
    private lateinit var getRepoListUseCaseShowBadge: GetRepoListUseCase
    private lateinit var getUserUseCase: GetUserUseCase
    private lateinit var getErrorRepoListUseCase: GetRepoListUseCase
    private lateinit var getErrorUserUseCase: GetUserUseCase

    private lateinit var viewModel: GithubViewModel
    private lateinit var viewModelError: GithubViewModel
    private lateinit var viewModelShowBadge: GithubViewModel

    @Before
    fun setUp() {
        getRepoListUseCase = GetRepoListUseCase(MockRepository.FakeSuccessRepo())
        getRepoListUseCaseShowBadge = GetRepoListUseCase(MockRepository.FakeSuccessRepo(showBadge = true))
        getUserUseCase =  GetUserUseCase(MockRepository.FakeSuccessRepo())
        getErrorRepoListUseCase = GetRepoListUseCase(MockRepository.FakeErrorRepo())
        getErrorUserUseCase = GetUserUseCase(MockRepository.FakeErrorRepo())

        viewModel = GithubViewModel(getRepoListUseCase, getUserUseCase)
        viewModelError = GithubViewModel(getErrorRepoListUseCase, getErrorUserUseCase)
        viewModelShowBadge = GithubViewModel(getRepoListUseCaseShowBadge, getUserUseCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test getUserAndRepoList success`() {
        val userId = "mockUserId"
        val expectedUserResponse= UIState.Success(MockRepository.FakeSuccessRepo.userResponse)
        val expectedRepoListResponse = UIState.Success(MockRepository.FakeSuccessRepo.listOfRepos)
        coroutineTestRule.runTest {
            // userStateFlow test
            val userStateFlow = MutableStateFlow<UIState<User>?>(null)
            userStateFlow.emit(expectedUserResponse)

            viewModel.getUserAndRepoList(userId)
            val expectedUserResult = userStateFlow.value
            val actualResult = viewModel.userStateFlow.value
            assert(actualResult is UIState.Success<User>)
            Assert.assertEquals(expectedUserResult, actualResult)

            // repoListStateFlow test
            val repoListStateFlow = MutableStateFlow<UIState<List<Repo>>?>(null)
            repoListStateFlow.emit(expectedRepoListResponse)

            viewModel.getUserAndRepoList(userId)
            val expectedRepoListResult = repoListStateFlow.value
            val actualRepoListResult = viewModel.repoListStateFlow.value
            assert(actualRepoListResult is UIState.Success<List<Repo>>)
            Assert.assertEquals(expectedRepoListResult, actualRepoListResult)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test getUserAndRepoList error`() {
        val userId = "mockUserId"
        val expectedUserResponse = UIState.Error(IllegalStateException())
        val expectedRepoListResponse = UIState.Error(IllegalStateException())
        coroutineTestRule.runTest {
            // userStateFlow test
            val userStateFlow = MutableStateFlow<UIState<User>?>(null)
            userStateFlow.emit(expectedUserResponse)

            viewModelError.getUserAndRepoList(userId)
            val expectedUserResult = userStateFlow.value
            val actualUserResult = viewModelError.userStateFlow.value
            assert(actualUserResult is UIState.Error)
            Assert.assertEquals(expectedUserResult?.javaClass, actualUserResult?.javaClass)

            // repoListStateFlow test
            val repoListStateFlow = MutableStateFlow<UIState<List<Repo>>?>(null)
            repoListStateFlow.emit(expectedRepoListResponse)

            viewModelError.getUserAndRepoList(userId)
            val expectedRepoListResult = repoListStateFlow.value
            val actualRepoListResult = viewModelError.repoListStateFlow.value
            assert(actualRepoListResult is UIState.Error)
            Assert.assertEquals(expectedRepoListResult?.javaClass, actualRepoListResult?.javaClass)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test getRepoListUseCase showBadge`() {
        val userId = "mockUserId"
        val showBadgeValue = true
        coroutineTestRule.runTest {
            // showBadgeFlow test
            val showBadgeFlow = MutableStateFlow(false)
            showBadgeFlow.emit(showBadgeValue)

            viewModelShowBadge.getUserAndRepoList(userId)
            val expectedShowBadgeResult = showBadgeFlow.value
            val actualShowBadgeResult = viewModelShowBadge.showBadge.value

            Assert.assertEquals(expectedShowBadgeResult, actualShowBadgeResult)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test getRepoListUseCase do not showBadge`() {
        val userId = "mockUserId"
        val showBadgeValue = false
        coroutineTestRule.runTest {
            // showBadgeFlow test
            val showBadgeFlow = MutableStateFlow(false)
            showBadgeFlow.emit(showBadgeValue)

            viewModel.getUserAndRepoList(userId)
            val expectedShowBadgeResult = showBadgeFlow.value
            val actualShowBadgeResult = viewModel.showBadge.value

            Assert.assertEquals(expectedShowBadgeResult, actualShowBadgeResult)
        }
    }
}
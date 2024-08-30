package com.binarystudios.githubclone.presentation.navgraph

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.binarystudios.githubclone.domain.model.Repo
import com.binarystudios.githubclone.presentation.CombinedUIState
import com.binarystudios.githubclone.presentation.GithubViewModel
import com.binarystudios.githubclone.presentation.details.DetailsScreen
import com.binarystudios.githubclone.presentation.home.HomeScreen
import com.binarystudios.githubclone.utils.Constants.REPO_BUNDLE_KEY

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Route.Home.route) {
        composable(
            route = Route.Home.route
        ) {
            val viewModel: GithubViewModel = hiltViewModel()
            val userUIState = viewModel.userStateFlow.collectAsState()
            val repoListUIState = viewModel.repoListStateFlow.collectAsState()
            val showBadge = viewModel.showBadge.collectAsState()
            val combinedUIState = remember {
                derivedStateOf {
                    CombinedUIState(userUIState.value, repoListUIState.value)
                }
            }
            HomeScreen(
                modifier = modifier,
                navController = navController,
                state = combinedUIState.value,
                showBadge = showBadge.value,
                onSearchClick = { userId ->
                    viewModel.getUserAndRepoList(userId)
                }
            )
        }
        composable(
            route = Route.Details.route
        ) { backStackEntry ->
            val repo = backStackEntry.arguments?.getParcelable(REPO_BUNDLE_KEY) as Repo?
            repo?.let {
                DetailsScreen(
                    modifier = modifier,
                    repoDetails = it
                )
            }
        }
    }
}

fun NavController.navigate(
    route: String,
    args: Bundle,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    val nodeId = graph.findNode(route = route)?.id
    if (nodeId != null) {
        navigate(nodeId, args, navOptions, navigatorExtras)
    }
}
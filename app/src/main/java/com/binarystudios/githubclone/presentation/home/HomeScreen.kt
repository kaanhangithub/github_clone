package com.binarystudios.githubclone.presentation.home

import android.os.Bundle
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.binarystudios.githubclone.R
import com.binarystudios.githubclone.domain.model.Repo
import com.binarystudios.githubclone.presentation.CombinedUIState
import com.binarystudios.githubclone.presentation.UIState
import com.binarystudios.githubclone.presentation.common.LoadingIndicator
import com.binarystudios.githubclone.presentation.home.components.SearchBar
import com.binarystudios.githubclone.presentation.home.components.UserDetails
import com.binarystudios.githubclone.presentation.navgraph.Route
import com.binarystudios.githubclone.presentation.navgraph.navigate
import com.binarystudios.githubclone.utils.Constants.REPO_BUNDLE_KEY

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    state: CombinedUIState,
    showBadge: Boolean,
    onSearchClick: (String) -> Unit
) {
    var text by remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar(
            text = text,
            onValueChange = { text = it },
            onSearch = {
                onSearchClick(text)
            }
        )

        HandleUIState(
            uiState = state,
            showBadge = showBadge,
            onRepoClick = {
                val bundle = Bundle().apply {
                    putParcelable(REPO_BUNDLE_KEY, it)
                }
                navController.navigate(
                    Route.Details.route,
                    bundle
                )
            }
        )
    }
}

@Composable
fun HandleUIState(
    uiState: CombinedUIState,
    showBadge: Boolean,
    onRepoClick: (response: Repo) -> Unit
) {
    AnimatedContent(
        targetState = uiState,
        transitionSpec = {
            fadeIn(
                animationSpec = tween(2000)
            ) togetherWith fadeOut(animationSpec = tween(2000))
        }
    ) { targetState ->
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when {
                targetState.userState is UIState.Loading || targetState.repoListState is UIState.Loading -> {
                    LoadingIndicator()
                }

                targetState.userState is UIState.Error || targetState.repoListState is UIState.Error -> {
                    ErrorText()
                }

                targetState.userState is UIState.Success && targetState.repoListState is UIState.Success -> {
                    val userResponse = targetState.userState.response
                    val repoResponse = targetState.repoListState.response

                    UserDetails(
                        userResponse = userResponse,
                        repoList = repoResponse,
                        showBadge = showBadge,
                        onRepoClick = onRepoClick
                    )
                }
            }
        }
    }
}

@Composable
fun ErrorText(
    modifier: Modifier = Modifier
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(id = R.string.error_state_text),
            color = Color.Red,
            modifier = modifier.padding(16.dp)
        )
    }
}
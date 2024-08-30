package com.binarystudios.githubclone.presentation.navgraph

sealed class Route(val route: String) {
    data object Home : Route("home")
    data object Details : Route("details")
}
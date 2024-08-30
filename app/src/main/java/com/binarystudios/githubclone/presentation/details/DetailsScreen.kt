package com.binarystudios.githubclone.presentation.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.binarystudios.githubclone.R
import com.binarystudios.githubclone.domain.model.Repo
import com.binarystudios.githubclone.ui.theme.GithubCloneTheme

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    repoDetails: Repo
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = repoDetails.name ?: "",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = repoDetails.description ?: "",
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(id = R.string.last_updated_text, repoDetails.updated_at ?: ""),
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(id = R.string.stars_text, repoDetails.stargazers_count),
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(id = R.string.forks_text, repoDetails.forks),
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    GithubCloneTheme {
        DetailsScreen(
            repoDetails = Repo(
                name = "Briefly",
                description = "Briefly is a modern news app that delivers the latest news from various sources. Built with Jetpack Compose, Room, Coroutines, MVVM + Clean Architecture, Hilt, Retrofit, and Paging 3, it offers a seamless and efficient user experience.",
                updated_at = "3 weeks ago",
                stargazers_count = 100,
                forks = 345
            )
        )
    }
}
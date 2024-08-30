package com.binarystudios.githubclone.presentation.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.binarystudios.githubclone.R
import com.binarystudios.githubclone.domain.model.Repo
import com.binarystudios.githubclone.domain.model.User

@Composable
fun UserDetails(
    modifier: Modifier = Modifier,
    userResponse: User,
    repoList: List<Repo>,
    showBadge: Boolean,
    onRepoClick: (response: Repo) -> Unit
) {
    val context = LocalContext.current
    AsyncImage(
        modifier = modifier
            .padding(top = 5.dp),
        model = ImageRequest.Builder(context).data(userResponse.avatar_url).build(),
        contentDescription = null
    )

    Row {
        Text(
            text = userResponse.name
                ?: stringResource(id = R.string.user_name_error_text),
            color = if (showBadge) colorResource(id = R.color.gold) else Color.Black
        )
        if (showBadge) {
            Spacer(modifier = modifier.width(8.dp))
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "",
                tint = colorResource(id = R.color.gold)
            )
        }
    }

    LazyColumn {
        items(repoList.size) {
            RepoItem(
                name = repoList[it].name,
                description = repoList[it].description,
                onClick = { onRepoClick(repoList[it]) }
            )
        }
    }
}
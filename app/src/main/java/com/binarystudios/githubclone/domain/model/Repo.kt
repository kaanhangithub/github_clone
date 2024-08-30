package com.binarystudios.githubclone.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Repo(
    val name: String?,
    val description: String?,
    val updated_at: String?,
    val stargazers_count: Int,
    val forks: Int,
) : Parcelable

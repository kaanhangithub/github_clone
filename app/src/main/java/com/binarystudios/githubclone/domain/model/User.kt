package com.binarystudios.githubclone.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String?,
    val avatar_url: String,
) : Parcelable

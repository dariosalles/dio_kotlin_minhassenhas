package com.dsxweb.minhassenhas.feature.listadesenhas.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserAdmin (
    val name: String,
    val email: String,
    val senha: String

) : Parcelable
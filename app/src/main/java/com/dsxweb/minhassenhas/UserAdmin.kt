package com.dsxweb.minhassenhas

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserAdmin (
    var name: String,
    var email: String,
    var senha: String

) : Parcelable
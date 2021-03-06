package com.dsxweb.minhassenhas

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Password (
    var login: String,
    var senha: String,
    var categoria: String,
    var observacao: String
) : Parcelable
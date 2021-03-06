package com.dsxweb.minhassenhas

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SenhasHelper(private val sharedPreferences: SharedPreferences) {

    fun getListContacts(): List<Password> {
        val list = sharedPreferences.getString("senhas", "[]")
        val formType = object : TypeToken<List<Password>>() {}.type
        return Gson().fromJson(list, formType)
    }

    fun setListContacts(list: List<Password>) {
        sharedPreferences.edit().putString("senhas", Gson().toJson(list)).commit()
    }
}
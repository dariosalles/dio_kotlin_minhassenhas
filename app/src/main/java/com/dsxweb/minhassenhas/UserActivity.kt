package com.dsxweb.minhassenhas

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity() {

    private val list: MutableList<UserAdmin> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        //getUser()
        lista()

    }

    // instancia o SharedPreferences
    private fun getInstaceSharedPreferences() : SharedPreferences {
        return getSharedPreferences("com.dsxweb.minhassenhas.PREFERENCES", Context.MODE_PRIVATE)
    }

    // puxa o usuario admin persistido
    private fun getUser(): List<UserAdmin> {

        val list = getInstaceSharedPreferences().getString("useradmin", "[]")

        Log.d(list.toString(), "User Admin")

        // vai fazer a conversão de uma lista de string para um objeto de classe
        val formType = object : TypeToken<List<UserAdmin>>() {}.type

        //return list
        return Gson().fromJson(list, formType)

    }

    private fun lista(){
        val list = getUser()
        txtEmail.text = list[0].email
        txtSenha.text = list[0].senha

        Log.d(list.toString(), "variavel list")

    }


}
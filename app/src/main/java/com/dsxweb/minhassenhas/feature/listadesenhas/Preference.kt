package com.dsxweb.minhassenhas.feature.listadesenhas

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.edit
import com.dsxweb.minhassenhas.bases.BaseActivity
import com.dsxweb.minhassenhas.feature.listadesenhas.model.UserAdmin
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_cadastro.*
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.activity_user.progress

class Preference(context: Context) : BaseActivity() {

    val PREFERENCE_NAME = "com.dsxweb.minhassenhas.PREFERENCES"
    val PREFERENCE_USER_ADMIN = "useradmin"

    val preference = context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE)

    fun verifyLogin() : Boolean {
        return preference.contains(PREFERENCE_USER_ADMIN)
    }

    fun getUserLogin(): UserAdmin {

        val login = preference.getString(PREFERENCE_USER_ADMIN, "[]")

        var userAdmin = Gson().fromJson(login, UserAdmin::class.java)

        val nomeSp = userAdmin.name //nome persistido
        val emailSp = userAdmin.email //email persistido
        val senhaSp = userAdmin.senha //senha persistida

        return UserAdmin(nomeSp,emailSp,senhaSp)

    }

    fun getLogin(email: String, senha: String) : Boolean {

        val login = preference.getString(PREFERENCE_USER_ADMIN, "[]")

        var userAdmin = Gson().fromJson(login, UserAdmin::class.java)

        val emailSp = userAdmin.email //email persistido
        val senhaSp = userAdmin.senha //senha persistida

        val eEmail = email //email form
        val eSenha = senha //senha form

        if(eEmail == emailSp && eSenha == senhaSp) {
            return true
        }else {
            return false
        }
    }

    fun setLogin(nome: String, email: String, senha: String): Boolean {

        //verificar campos vazios
        val n = nome
        val e = email
        val s = senha

        var jsonString = Gson().toJson(UserAdmin(n,e,s))

        val editor = preference.edit()
        editor.putString(PREFERENCE_USER_ADMIN, jsonString)
        editor.commit()

        return true


    }

    fun clearLogin() : Boolean {
        preference.edit{
            clear()
        }
        return true
    }
}
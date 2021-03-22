package com.dsxweb.minhassenhas.feature.listadesenhas

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.edit
import com.dsxweb.minhassenhas.R
import com.dsxweb.minhassenhas.application.SenhasApplication
import com.dsxweb.minhassenhas.bases.BaseActivity
import com.dsxweb.minhassenhas.feature.listadesenhas.model.UserAdmin
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_senha.*
import kotlinx.android.synthetic.main.activity_senhas.*
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.activity_user.progress
import kotlinx.android.synthetic.main.activity_user.toolBar


class UserActivity : BaseActivity() {

    private var userAdmin: MutableList<UserAdmin> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        setupToolBar(toolBar, "Configurações", true)
        getAdmin()

        btnChangePass.setOnClickListener {
            val change = Preference(this).setLogin(txtNome.text.toString(), txtEmail.text.toString(), txtSenha.text.toString())
            if(change){

                showToast("Senha mestre atualizada com sucesso")
                val intent = Intent(this, SenhasActivity::class.java)
                startActivity(intent)

            } else {
                showToast("Erro ao Atualizar")
            }
        }

        btnClear.setOnClickListener {
            val clear = Preference(this).clearLogin()
            if(clear){

                showToast("Usuário excluido com sucesso")

                Thread.sleep(1500)

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                showToast("Erro ao excluir o usuário")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getAdmin()
    }

    // instancia o SharedPreferences
    private fun getInstaceSharedPreferences() : SharedPreferences {
        return getSharedPreferences("com.dsxweb.minhassenhas.PREFERENCES", Context.MODE_PRIVATE)
    }

    // puxa o usuario admin persistido
    private fun getUserAdmin(): List<UserAdmin> {

        val list = getInstaceSharedPreferences().getString("useradmin", "[]")

        Log.d(list.toString(), "User Admin Shared P")

        // vai fazer a conversão de uma lista de string para um objeto de classe
        val formType = object : TypeToken<List<UserAdmin>>() {}.type

        //return list
        return Gson().fromJson(list, formType)

    }

    private fun setUserAdmin() {

        val nome = txtNome.text.toString()
        val email = txtEmail.text.toString()
        val senha = txtSenha.text.toString()

        userAdmin.add(0, UserAdmin(nome,email,senha))

        progress.visibility = View.VISIBLE

        Thread(Runnable {
            Thread.sleep(1500)

            getInstaceSharedPreferences().edit() {
                //transforma o lista de objeto em Json
                //importacao da dependencia no gradle GSON
                val json = Gson().toJson(userAdmin)
                putString("useradmin", json)
                commit() //grava a persistencia em uma thread separada evitando assim ao consultar os dados que a thread tenha finalizado
            }

            runOnUiThread {
                progress.visibility = View.GONE
            }
        }).start()

    }

    private fun getAdmin(){

        val userAdmin = Preference(this).getUserLogin()

        txtNome.setText(userAdmin.name)
        txtEmail.setText(userAdmin.email)
        txtSenha.setText(userAdmin.senha)

    }

    private fun changePass() {

        val nome = txtNome.text
        val email = txtEmail.text
        val senha = txtSenha.text

        showToast(nome.toString())

        setUserAdmin()
    }

}
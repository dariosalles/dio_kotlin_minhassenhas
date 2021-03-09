package com.dsxweb.minhassenhas

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_user.*

class MainActivity : AppCompatActivity() {

    // email e senha padrao - inicial
    var userAdmin: MutableList<UserAdmin> = mutableListOf()

    fun fakeUser(){
        userAdmin.add(0, UserAdmin("Dario","admin@admin.com","123456"))
        Log.d(userAdmin.toString(), "User admin")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fakeUser() // cria o usuário fake
        setUser()
        setListeners()

    }

    // instancia o SharedPreferences
    private fun getInstaceSharedPreferences() : SharedPreferences {
        return getSharedPreferences("com.dsxweb.minhassenhas.PREFERENCES", Context.MODE_PRIVATE)
    }

    fun setUser() {

        getInstaceSharedPreferences().edit {
            //transforma o lista de objeto em Json
            //importacao da dependencia no gradle GSON
            val json = Gson().toJson(userAdmin)
            putString("useradmin", json)
            commit() //grava a persistencia em uma thread separada evitando assim ao consultar os dados que a thread tenha finalizado
        }


    }

    // função para controlar as acões
    fun setListeners() {
        btnLogin.setOnClickListener {
            Login(inputEmail.text.toString(),inputSenha.text.toString())
        }
    }

    // funcao de mensagens TOAST
    fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // função de login
    fun Login(email: String, senha: String){

            val emailUser = userAdmin[0].email
            val senhaUser = userAdmin[0].senha

        Log.d(email, "Email")
        Log.d(senha, "Senha")

            if(email == emailUser && senha == senhaUser){
                //carrega outra activity
                val intent = Intent(this, SenhasActivity::class.java)
                startActivity(intent)

            } else {

                //mensagem de erro
                showToast("Email ou senha inválidos")
            }
        }


}
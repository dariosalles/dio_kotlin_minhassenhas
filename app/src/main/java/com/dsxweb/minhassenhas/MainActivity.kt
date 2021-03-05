package com.dsxweb.minhassenhas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // email e senha padrao - inicial
    val emailDef: String = "email@email.com"
    val senhaDef: String = "12345"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setListeners()
    }

    // função para controlar as acões
    fun setListeners() {
        btnLogin.setOnClickListener {
            Login(inputEmail.text.toString(),inputSenha.text.toString())
        }
    }

    // funcao de mensagens TOAST
    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // função de login
    fun Login(email: String, senha: String){

        if(email == emailDef && senha == senhaDef){

        //carrega outra activity
            val intent = Intent(this, Senhas::class.java)
            startActivity(intent)

        } else {
            //mensagem de erro
            showToast("Erro")
        }

    }
}
package com.dsxweb.minhassenhas.feature.listadesenhas

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.edit
import com.dsxweb.minhassenhas.R
import com.dsxweb.minhassenhas.feature.listadesenhas.model.Password
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_cadastro.*

class CadastroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        setListeners()
        initToolBar()
    }

    // instancia o SharedPreferences
    private fun getInstaceSharedPreferences() : SharedPreferences {
        return getSharedPreferences("com.dsxweb.minhassenhas.PREFERENCES", Context.MODE_PRIVATE)
    }

    fun setListeners() {
        btnCadastrar.setOnClickListener {
            val txtLogin = inputLogintxt.text
            val txtSenha = inputSenhatxt.text
            val txtCategoria = inputCategoriatxt.text
            val txtObs = inputObs.text

            println(txtLogin)
            println(txtSenha)
            println(txtCategoria)
            println(txtObs)

            val cadastra: Password = Password(1,txtLogin.toString(), txtSenha.toString(), txtObs.toString(),txtObs.toString())

            getInstaceSharedPreferences().edit {
                //transforma o lista de objeto em Json
                //importacao da dependencia no gradle GSON
                val json = Gson().toJson(cadastra)
                putString("senhas", json)
                commit() //grava a persistencia em uma thread separada evitando assim ao consultar os dados que a thread tenha finalizado
            }

            updateList()

        }

    }


    private fun getListSenhas(): List<Password> {
        val list = getInstaceSharedPreferences().getString("senhas", "[]")

        // vai fazer a conversão de uma lista de string para um objeto de classe
        val formType = object : TypeToken<List<Password>>() {}.type


        return Gson().fromJson(list, formType)
    }

    private fun updateList() {
        val list = getListSenhas()
        println(list.toString())
    }

    private fun initToolBar(){
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // fecha a activity atraves do botao voltar
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
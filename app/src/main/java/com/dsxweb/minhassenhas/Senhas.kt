package com.dsxweb.minhassenhas

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.edit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dsxweb.minhassenhas.SenhasDetail.Companion.EXTRA_CONTACT
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Senhas : AppCompatActivity(), ClickItemSenhasListener {

    private val rvList: RecyclerView by lazy {
        findViewById<RecyclerView>(R.id.rv_list)
    }

    private val adapter = SenhasAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_senhas)

        initToolBar()
        listSenhas()
        bindView()
    }

    // instancia o SharedPreferences
    private fun getInstaceSharedPreferences() : SharedPreferences {
        return getSharedPreferences("com.dsxweb.minhassenhas.PREFERENCES", Context.MODE_PRIVATE)
    }

    //gera uma lista de senhas qualquer - simular api
    private fun listSenhas() {
        var list = arrayListOf(
            Password(
                "email@gmail.com",
                "123456",
                "Social",
                "Gmail"
            ),
            Password(
                "email@hotmail.com",
                "123456",
                "Social",
                "Facebook"
            )
        )

        // insere o objeto acima de senhas no SharedPreferences
        getInstaceSharedPreferences().edit {
            //transforma o lista de objeto em Json
            //importacao da dependencia no gradle gson
            val json = Gson().toJson(list)
            putString("senhas", json)
            commit() //grava a persistencia em uma thread separada evitando assim ao consultar os dados que a thread tenha finalizado
        }

    }

    private fun bindView() {
        rvList.adapter = adapter
        rvList.layoutManager = LinearLayoutManager(this)
        updateList()
    }

    // puxa a lista de senhas persistidas
    private fun getListSenhas(): List<Password> {
        val list = getInstaceSharedPreferences().getString("senhas", "[]")

        // vai fazer a conversão de uma lista de string para um objeto de classe
        val formType = object : TypeToken<List<Password>>() {}.type
        return Gson().fromJson(list, formType)
    }

    private fun updateList() {
        val list = getListSenhas()
        adapter.updateList(list)
    }

    // botao voltar - inicia ele no toolbar
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

    override fun clickItemSenha(senha: Password) {
        //navegar pra outra tela
        val intent = Intent(this, SenhasDetail::class.java)
        intent.putExtra(EXTRA_CONTACT, senha)

        startActivity(intent)
    }
}
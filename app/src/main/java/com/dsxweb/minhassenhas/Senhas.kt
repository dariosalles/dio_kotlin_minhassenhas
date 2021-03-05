package com.dsxweb.minhassenhas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar

class Senhas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_senhas)

        initToolBar()
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
}
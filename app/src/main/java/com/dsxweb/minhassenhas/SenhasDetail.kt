package com.dsxweb.minhassenhas


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class SenhasDetail : AppCompatActivity() {

    private var senha: Password? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_senhas_detail)

        initToolbar()
        getExtras()
        bindViews()
    }

    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        //botao voltar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    //recupera os dados vindos da outra tela
    private fun getExtras() {
        senha = intent.getParcelableExtra(EXTRA_CONTACT)
    }

    // procura as ids na tela e substitui pelo que vem da função getExtras (Outra tela)
    private fun bindViews() {
        findViewById<TextView>(R.id.tv_Login).text = senha?.login
        findViewById<TextView>(R.id.tv_Senha).text = senha?.senha
        findViewById<TextView>(R.id.tv_Categoria).text = senha?.categoria
        findViewById<TextView>(R.id.tv_Obs).text = senha?.observacao

    }

    companion object {
        const val EXTRA_CONTACT: String = "EXTRA_CONTACT"
    }

    // método responsável pelo botão voltar
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
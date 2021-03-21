package com.dsxweb.minhassenhas.feature.listadesenhas

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.content.edit
import com.dsxweb.minhassenhas.R
import com.dsxweb.minhassenhas.bases.BaseActivity
import com.dsxweb.minhassenhas.feature.listadesenhas.model.Password
import com.dsxweb.minhassenhas.feature.listadesenhas.model.UserAdmin
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_cadastro.*
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.activity_user.progress
import kotlinx.android.synthetic.main.activity_user.toolBar
import kotlinx.android.synthetic.main.activity_user.txtEmail
import kotlinx.android.synthetic.main.activity_user.txtSenha

class CadastroActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        setupToolBar(toolBar, "Cadastro Senha Mestre", true)

        //recupera os dados vindos da outra tela
        getExtras()

        btnSalvar.setOnClickListener {

            val nome = etNome.text.toString()
            val email = etEmail.text.toString()
            val senha = etSenha.text.toString()

            if(nome.isNotEmpty() && email.isNotEmpty() && senha.isNotEmpty()) {

                val save = Preference(this).setLogin(etNome.text.toString(), etEmail.text.toString(), etSenha.text.toString())

                if(save){
                    showToast("Cadastro feito com sucesso")
                    finish()
                } else {
                    showToast("Erro ao cadastrar")
                }
            } else {
                showToast("Preencha Nome, Email e Senha")
            }

            //myp.setLogin(etNome.text.toString(),etEmail.text.toString(),etSenha.text.toString()) }
        }
    }

    private fun getExtras() {
        val email: String = intent.getStringExtra("email").toString()
        val senha: String = intent.getStringExtra("senha").toString()

        println(email)
        println(senha)

        etEmail.setText(email)
        etSenha.setText(senha)
    }



}
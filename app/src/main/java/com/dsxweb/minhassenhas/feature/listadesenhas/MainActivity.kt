package com.dsxweb.minhassenhas.feature.listadesenhas

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.dsxweb.minhassenhas.R
import com.dsxweb.minhassenhas.bases.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.view.*


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //CLEAR SHARED PREFERENCES
        //Preference(this).clearLogin()

        //VERIFICA SE JÁ EXISTE UMA SENHA MESTRE CADASTRADA
        val rp = Preference(this ).verifyLogin()
        if(rp){
            LayoutCheckbox.visibility = View.GONE
            LayoutNome.visibility = View.GONE
            btnCadastrar.visibility = View.GONE

        } else {

            btnLogin.visibility = View.GONE
        }

        btnCadastrar.setOnClickListener {

            val nome = txtNome.text.toString()
            val email = txtEmail.text.toString()
            val senha = txtSenha.text.toString()

            if(nome.isNotEmpty() && email.isNotEmpty() && senha.isNotEmpty()) {

                val save = Preference(this).setLogin(nome, email, senha)

                if(save){
                    showToast("Cadastro feito com sucesso")
                    //finish()
                } else {
                    showToast("Erro ao cadastrar")
                }
            } else {
                showToast("Preencha Nome, Email e Senha")
            }

        }

        // ACAO AO CLICAR NO BOTAO ACESSARdariosalles
        btnLogin.setOnClickListener {

            val tEmail = LayoutEmail.txtEmail.text.toString()
            val tSenha = LayoutSenha.txtSenha.text.toString()

            if(tEmail.isNotEmpty() && tSenha.isNotEmpty()){

                var login = Preference(this).getLogin(tEmail,tSenha)

                if(login){
                    val intent = Intent(this, SenhasActivity::class.java)
                    startActivity(intent)
                } else {
                    showToast("Erro ao conectar")
                }

            } else {
                showToast("Preencha os campos email e senha")
            }


        }

        // ACAO AO CLICAR NO BOTAO CADASTRAR
        btnCadastrar.setOnClickListener {

            val nome = txtNome.text.toString()
            val email = txtEmail.text.toString()
            val senha = txtSenha.text.toString()

            if(nome.isNotEmpty() && email.isNotEmpty() && senha.isNotEmpty()) {

                val save = Preference(this).setLogin(nome, email, senha)

                if(save){
                    showToast("Cadastro feito com sucesso")
                    onResume()
                } else {
                    showToast("Erro ao cadastrar")
                }
            } else {
                showToast("Preencha Nome, Email e Senha")
            }

        }

    }

    override fun onResume() {
        super.onResume()

        val rp = Preference(this ).verifyLogin()
        if(rp){
            LayoutNome.visibility = View.GONE
            LayoutCheckbox.visibility = View.VISIBLE
            btnCadastrar.visibility = View.GONE
            btnLogin.visibility = View.VISIBLE

        } else {
            btnCadastrar.visibility = View.VISIBLE
            btnLogin.visibility = View.GONE
        }

    }

}
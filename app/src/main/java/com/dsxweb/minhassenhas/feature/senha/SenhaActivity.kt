package com.dsxweb.minhassenhas.feature.senha

import android.os.Bundle
import android.view.View
import com.dsxweb.minhassenhas.R
import com.dsxweb.minhassenhas.application.SenhasApplication
import com.dsxweb.minhassenhas.bases.BaseActivity
import com.dsxweb.minhassenhas.feature.listadesenhas.model.Password
import kotlinx.android.synthetic.main.activity_senha.*
import kotlinx.android.synthetic.main.activity_senha.toolBar

class SenhaActivity : BaseActivity() {

    private var idSenha: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_senha)

        setupToolBar(toolBar, "Lista de Senhas",true)
        setupSenha()
        btnSalvarSenha.setOnClickListener { onClickSalvarSenha() }
        btnExcluirSenha.setOnClickListener { onClickExcluirSenha() }
    }

    private fun setupSenha(){
        idSenha = intent.getIntExtra("index",-1)
        if (idSenha == -1){
            btnExcluirSenha.visibility = View.GONE
            return
        }
        progress.visibility = View.VISIBLE
        Thread(Runnable {
            Thread.sleep(1500)
            var lista = SenhasApplication.instance.helperDB?.buscarContatos("$idSenha",true) ?: return@Runnable
            var senha = lista.getOrNull(0) ?: return@Runnable
            runOnUiThread {
                etLogin.setText(senha.login)
                etSenha.setText(senha.senha)
                etCategoria.setText(senha.categoria)
                etObs.setText(senha.observacao)
                progress.visibility = View.GONE
            }
        }).start()
    }

    private fun onClickSalvarSenha(){
        val login = etLogin.text.toString()
        val senhav = etSenha.text.toString()
        val categoria = etCategoria.text.toString()
        val obs = etObs.text.toString()

        val senha = Password(
            idSenha,
            login,
            senhav,
            categoria,
            obs
        )
        progress.visibility = View.VISIBLE
        Thread(Runnable {
            Thread.sleep(1500)
            if(idSenha == -1) {
                SenhasApplication.instance.helperDB?.salvarSenha(senha)
            }else{
                SenhasApplication.instance.helperDB?.updateSenha(senha)
            }
            runOnUiThread {
                progress.visibility = View.GONE
                finish()
            }
        }).start()
    }

    private fun onClickExcluirSenha() {
        if(idSenha > -1){
            progress.visibility = View.VISIBLE
            Thread(Runnable {
                Thread.sleep(1500)
                SenhasApplication.instance.helperDB?.deletarSenha(idSenha)
                runOnUiThread {
                    progress.visibility = View.GONE
                    finish()
                }
            }).start()
        }
    }
}

package com.dsxweb.minhassenhas.feature.listadesenhas

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.core.content.edit
import com.dsxweb.minhassenhas.R
import com.dsxweb.minhassenhas.bases.BaseActivity
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

        btnSalvar.setOnClickListener {
            val save = Preference(this).setLogin(etNome.text.toString(), etEmail.text.toString(), etSenha.text.toString())

            if(save){
                showToast("Cadastro feito com sucesso")
                finish()
            } else {
                showToast("Erro ao cadastrar")
            }
            //myp.setLogin(etNome.text.toString(),etEmail.text.toString(),etSenha.text.toString()) }
        }
    }


}
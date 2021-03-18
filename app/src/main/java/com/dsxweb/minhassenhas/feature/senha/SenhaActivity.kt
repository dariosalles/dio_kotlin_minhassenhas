package com.dsxweb.minhassenhas.feature.senha

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.dsxweb.minhassenhas.R
import com.dsxweb.minhassenhas.application.SenhasApplication
import com.dsxweb.minhassenhas.bases.BaseActivity
import com.dsxweb.minhassenhas.feature.listadesenhas.model.Password
import kotlinx.android.synthetic.main.activity_senha.*
import kotlinx.android.synthetic.main.activity_senha.toolBar

class SenhaActivity : BaseActivity() {

    private var idSenha: Int = -1

    var categorias = arrayOf("Banco", "Email", "Entretenimento", "Internet", "Jogos", "Outro", "Social", "Trabalho")
    var catSelecionado: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_senha)

        setupToolBar(toolBar, "Lista de Senhas",true)
        setupSenha()
        setupSpinner(categoria = "")
        btnSalvarSenha.setOnClickListener { onClickSalvarSenha() }
        btnExcluirSenha.setOnClickListener { onClickExcluirSenha() }

    }

    private fun setupSenha(){

        idSenha = intent.getIntExtra("index",-1)

        if (idSenha == -1){ // novo cadastro
            btnExcluirSenha.visibility = View.GONE
            //layout_categoria.visibility = View.GONE
            return
        }
        progress.visibility = View.VISIBLE

        Thread(Runnable {
            Thread.sleep(1500)
            var lista = SenhasApplication.instance.helperDB?.buscarContatos("$idSenha",true) ?: return@Runnable
            var senha = lista.getOrNull(0) ?: return@Runnable

            runOnUiThread {
                setupSpinner(senha.categoria)
                etLogin.setText(senha.login)
                etSenha.setText(senha.senha)
                //etCategoria.setText(senha.categoria)
                etObs.setText(senha.observacao)
                progress.visibility = View.GONE

            }
        }).start()
    }

    fun setupSpinner(categoria: String) {

        val spinner: Spinner = findViewById(com.dsxweb.minhassenhas.R.id.sp_categoria)

        val categoriasAll = arrayOf(categoria) + this.categorias

        idSenha = intent.getIntExtra("index",-1)
        if (idSenha == -1) { // novo cadastro
            spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categorias)
        } else { // edição de cadastro
            spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categoriasAll)
        }

        spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)
                if(idSenha == -1){
                    catSelecionado = categorias[pos]
                } else {
                    catSelecionado = categoriasAll[pos]
                }

                //showToast(catSelecionado)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }
        //FIM SPINNER
    }

    private fun onClickSalvarSenha(){

        val login = etLogin.text.toString()
        val senhav = etSenha.text.toString()
        val scategoria = catSelecionado
        val obs = etObs.text.toString()

        val senha = Password(
            idSenha,
            login,
            senhav,
            scategoria,
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

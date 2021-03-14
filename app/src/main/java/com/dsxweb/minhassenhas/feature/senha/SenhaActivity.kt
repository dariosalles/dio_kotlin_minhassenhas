package com.dsxweb.minhassenhas.feature.senha

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.StringRes
import com.dsxweb.minhassenhas.R
import com.dsxweb.minhassenhas.application.SenhasApplication
import com.dsxweb.minhassenhas.bases.BaseActivity
import com.dsxweb.minhassenhas.feature.listadesenhas.model.Password
import com.dsxweb.minhassenhas.utils.SpinnerActivity
import kotlinx.android.synthetic.main.activity_senha.*
import kotlinx.android.synthetic.main.activity_senha.toolBar

class SenhaActivity : BaseActivity() {

    private var idSenha: Int = -1


    var categorias = arrayOf("Banco", "Email", "Entretenimento","Internet", "Jogos", "Outro", "Social", "Trabalho")
    var categoria: String = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_senha)

        setupToolBar(toolBar, "Lista de Senhas",true)
        setupSenha()
        setupSpinner()
        btnSalvarSenha.setOnClickListener { onClickSalvarSenha() }
        btnExcluirSenha.setOnClickListener { onClickExcluirSenha() }

    }

    fun setupSpinner() {


        val spinner: Spinner = findViewById(com.dsxweb.minhassenhas.R.id.sp_categoria)

        //Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
                this,
                //categorias,
                R.array.categorias,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)
                categoria = categorias[pos]

                showToast(categoria.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }

        //FIM SPINNER
    }



    private fun setupSenha(){


        idSenha = intent.getIntExtra("index",-1)
        if (idSenha == -1){
            btnExcluirSenha.visibility = View.GONE
            layout_categoria.visibility = View.GONE
            return
        }
        progress.visibility = View.VISIBLE
        Thread(Runnable {
            Thread.sleep(1500)
            var lista = SenhasApplication.instance.helperDB?.buscarContatos("$idSenha",true) ?: return@Runnable
            var senha = lista.getOrNull(0) ?: return@Runnable

            //val c = categorias.set(8,categoria)

            //verifica a categoria

            runOnUiThread {
                etLogin.setText(senha.login)
                etSenha.setText(senha.senha)
                etCategoria.setText(senha.categoria)
                etObs.setText(senha.observacao)
                progress.visibility = View.GONE
                sp_categoria.visibility = View.GONE
                layout_spinner.visibility = View.GONE
            }
        }).start()
    }

    private fun onClickSalvarSenha(){

        val login = etLogin.text.toString()
        val senhav = etSenha.text.toString()
        val scategoria = categoria
        //val categoria = etCategoria.text.toString()
        val obs = etObs.text.toString()

        println(scategoria)

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

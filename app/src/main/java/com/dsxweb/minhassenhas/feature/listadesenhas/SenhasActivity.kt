package com.dsxweb.minhassenhas.feature.listadesenhas

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.dsxweb.minhassenhas.R
import com.dsxweb.minhassenhas.application.SenhasAplication
import com.dsxweb.minhassenhas.bases.BaseActivity
import com.dsxweb.minhassenhas.feature.listadesenhas.adapter.SenhaAdapter
import com.dsxweb.minhassenhas.feature.listadesenhas.model.ContatosVO
import com.dsxweb.minhassenhas.feature.listadesenhas.model.Password
import com.dsxweb.minhassenhas.feature.senha.SenhaActivity
import kotlinx.android.synthetic.main.activity_senhas.*
import java.lang.Exception

class SenhasActivity : BaseActivity() {

    private var adapter: SenhaAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_senhas)

        setupToolBar(toolBar, "Lista de Senhas",false)
        setupListView()
        setupOnClicks()
        //bindView()
    }

    private fun setupOnClicks(){
        fab.setOnClickListener { onClickAdd() }
        ivBuscar.setOnClickListener { onClickBuscar() }
    }

    private fun setupListView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        onClickBuscar()
    }

    private fun onClickAdd(){
        val intent = Intent(this, SenhaActivity::class.java)
        startActivity(intent)
    }

    private fun onClickItemRecyclerView(index: Int) {
        val intent = Intent(this, SenhasActivity::class.java)
        intent.putExtra("index", index)
        startActivity(intent)
    }

    private fun onClickBuscar(){
        val busca = etBuscar.text.toString()
        progress.visibility = View.VISIBLE
        Thread(Runnable {
            Thread.sleep(1500)
            var listaFiltrada: List<Password> = mutableListOf()
            try {
                listaFiltrada = SenhasAplication.instance.helperDB?.buscarContatos(busca) ?: mutableListOf()
            }catch (ex: Exception){
                ex.printStackTrace()
            }
            runOnUiThread {
                adapter = SenhaAdapter(this,listaFiltrada) {onClickItemRecyclerView(it)}
                recyclerView.adapter = adapter
                progress.visibility = View.GONE
                Toast.makeText(this,"Buscando por $busca",Toast.LENGTH_SHORT).show()
            }
        }).start()
    }










//    // instancia o SharedPreferences
//    private fun getInstaceSharedPreferences() : SharedPreferences {
//        return getSharedPreferences("com.dsxweb.minhassenhas.PREFERENCES", Context.MODE_PRIVATE)
//    }
//
////    private fun bindView() {
////        rvList.adapter = adapter
////        rvList.layoutManager = LinearLayoutManager(this)
////        updateList()
////    }
//
//    // puxa a lista de senhas persistidas
//    private fun getListSenhas(): List<Password> {
//        val list = getInstaceSharedPreferences().getString("senhas", "[]")
//
//        // vai fazer a conversão de uma lista de string para um objeto de classe
//        val formType = object : TypeToken<List<Password>>() {}.type
//
//
//        return Gson().fromJson(list, formType)
//    }

//    private fun updateList() {
//        val list = getListSenhas()
//        adapter?.updateList(list)
//    }

    // botao voltar - inicia ele no toolbar
//    private fun initToolBar(){
//        val toolbar = findViewById<Toolbar>(R.id.toolbar)
//        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//    }
//    // fecha a activity atraves do botao voltar
//    override fun onSupportNavigateUp(): Boolean {
//        finish()
//        return true
//    }

//    override fun clickItemSenha(senha: Password) {
//        //navegar pra outra tela
//        val intent = Intent(this, SenhasDetail::class.java)
//        intent.putExtra(EXTRA_CONTACT, senha)
//
//        startActivity(intent)
//    }


}
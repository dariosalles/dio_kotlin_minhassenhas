package com.dsxweb.minhassenhas.feature.listadesenhas

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.dsxweb.minhassenhas.R
import com.dsxweb.minhassenhas.application.SenhasApplication
import com.dsxweb.minhassenhas.bases.BaseActivity
import com.dsxweb.minhassenhas.feature.listadesenhas.adapter.SenhaAdapter
import com.dsxweb.minhassenhas.feature.listadesenhas.model.Password
import com.dsxweb.minhassenhas.feature.senha.SenhaActivity
import kotlinx.android.synthetic.main.activity_senhas.*


class SenhasActivity : BaseActivity() {

    private var adapter:SenhaAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_senhas)

        setupToolBar(toolBar, "Lista de Senhas", true)
        setupListView()
        setupOnClicks()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id: Int = item.getItemId()
        return if (id == R.id.config) {

            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)

            showToast("Configurações")
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun setupOnClicks() {

        fab.setOnClickListener { onClickAdd() }
        ivBuscar.setOnClickListener { onClickBuscar() }
    }

    private fun setupListView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        onClickBuscar()
    }

    private fun onClickAdd() {
        val intent = Intent(this, SenhaActivity::class.java)
        startActivity(intent)
    }

    private fun onClickItemRecyclerView(index: Int) {
        val intent = Intent(this, SenhaActivity::class.java)
        intent.putExtra("index", index)
        startActivity(intent)
    }

    private fun onClickBuscar() {
        val busca = etBuscar.text.toString()
        progress.visibility = View.VISIBLE
        Thread(Runnable {
            Thread.sleep(1500)
            var listaFiltrada: List<Password> = mutableListOf()
            try {
                listaFiltrada =
                    SenhasApplication.instance.helperDB?.buscarContatos(busca) ?: mutableListOf()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            runOnUiThread {
                adapter = SenhaAdapter(this, listaFiltrada) { onClickItemRecyclerView(it) }
                recyclerView.adapter = adapter
                progress.visibility = View.GONE
                Toast.makeText(this, "Buscando por $busca", Toast.LENGTH_SHORT).show()
            }
        }).start()
    }
}
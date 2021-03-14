package com.dsxweb.minhassenhas.utils

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.dsxweb.minhassenhas.R
import com.dsxweb.minhassenhas.bases.BaseActivity
import com.dsxweb.minhassenhas.feature.listadesenhas.MainActivity

open class SpinnerActivity: BaseActivity() {

    //SPINNER

    //val categorias = resources.getStringArray(R.array.categorias)
    val categorias = arrayOf("Banco", "Jogos", "Internet")
    var categoria: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupSpinner()
    }

    fun setupSpinner() {

        val spinner: Spinner = findViewById(com.dsxweb.minhassenhas.R.id.sp_categoria)

        //Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
                this,
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
                showToast(categoria)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }

        //FIM SPINNER
    }

}
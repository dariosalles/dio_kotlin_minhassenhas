package com.dsxweb.minhassenhas.feature.listadesenhas.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.view.LayoutInflater
import com.dsxweb.minhassenhas.R
import com.dsxweb.minhassenhas.feature.listadesenhas.model.Password
import kotlinx.android.synthetic.main.item_contato.view.*
import kotlinx.android.synthetic.main.senhas_item.view.llItem

class SenhaAdapter(
        private val context: Context,
        private val lista: List<Password>,
        private val onClick: ((Int) -> Unit)
) : RecyclerView.Adapter<ContatoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_contato,parent,false)
        return ContatoViewHolder(view)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: ContatoViewHolder, position: Int) {
        val senha = lista[position]
        with(holder.itemView){
            tvObs.text = senha.observacao
            tvLogin.text = senha.login
            llItem.setOnClickListener { onClick(senha.id) }
        }
    }

}

class ContatoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
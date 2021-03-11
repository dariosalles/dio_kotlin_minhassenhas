package com.dsxweb.minhassenhas.feature.listadesenhas.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dsxweb.minhassenhas.R
import com.dsxweb.minhassenhas.feature.listadesenhas.model.Password
import kotlinx.android.synthetic.main.item_senha.view.*


class SenhaAdapter(
        private val context: Context,
        private val lista: List<Password>,
        private val onClick: ((Int) -> Unit)
) : RecyclerView.Adapter<SenhaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SenhaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_senha,parent,false)
        return SenhaViewHolder(view)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: SenhaViewHolder, position: Int) {
        val senha = lista[position]
        with(holder.itemView){
            tvObs.text = senha.observacao
            tvLogin.text = senha.login
            llItem.setOnClickListener { onClick(senha.id) }
        }
    }

}

class SenhaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
package com.dsxweb.minhassenhas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SenhasAdapter(var listener: ClickItemSenhasListener) :RecyclerView.Adapter<SenhasAdapter.SenhasAdapterViewHolder>() {

    // armazenar a lista de senhas
    private val list: MutableList<Password> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SenhasAdapterViewHolder {
        // criar a view / qual layout / qual xml
        val view = LayoutInflater.from(parent.context).inflate(R.layout.senhas_item, parent, false)
        return SenhasAdapterViewHolder(view, list, listener)
    }

    override fun getItemCount(): Int {
        //tamanho da lista de senhas
        return list.size
    }

    override fun onBindViewHolder(holder: SenhasAdapterViewHolder, position: Int) {
        // popular a view
        holder.bind(list[position])
    }

    fun updateList(list: List<Password>) {

        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()

    }

    // responsável por gerenciar cada item da lista
    class SenhasAdapterViewHolder(itemView: View,
                                   var list: List<Password>,
                                   var listener: ClickItemSenhasListener) : RecyclerView.ViewHolder(itemView) {

        private val tvLogin: TextView = itemView.findViewById(R.id.tv_Login)
        private val tvSenha: TextView = itemView.findViewById(R.id.tv_Senha)
        //private val ivPhotograph: ImageView = itemView.findViewById(R.id.iv_photograph)

        init {
            itemView.setOnClickListener {
                listener.clickItemSenha(list[adapterPosition])
            }
        }

        fun bind(senha: Password){
            tvLogin.text = senha.login
            tvSenha.text = senha.senha
        }
    }
}
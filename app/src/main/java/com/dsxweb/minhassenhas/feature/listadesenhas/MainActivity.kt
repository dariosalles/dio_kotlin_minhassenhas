package com.dsxweb.minhassenhas.feature.listadesenhas

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.dsxweb.minhassenhas.R
import com.dsxweb.minhassenhas.bases.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.view.*


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //CLEAR SHARED PREFERENCES
        //Preference(this).clearLogin()

        //VERIFICA SE JÁ EXISTE UMA SENHA MESTRE CADASTRADA
        val rp = Preference(this ).verifyLogin()
        if(rp){

            btnCadastrar.visibility = View.GONE
        } else {
            btnLogin.visibility = View.GONE
        }

        // ACAO AO CLICAR NO BOTAO ACESSAR
        btnLogin.setOnClickListener {



            val tEmail = LayoutEmail.txtEmail.text.toString()
            val tSenha = LayoutSenha.txtSenha.text.toString()

            if(tEmail.isNotEmpty() && tSenha.isNotEmpty()){

                var login = Preference(this).getLogin(tEmail,tSenha)

                if(login){
                    val intent = Intent(this, SenhasActivity::class.java)
                    startActivity(intent)
                } else {
                    showToast("Erro ao conectar")
                }

            } else {
                showToast("Preencha os campos email e senha")
            }


        }

        // ACAO AO CLICAR NO BOTAO CADASTRAR
        btnCadastrar.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()

        val rp = Preference(this ).verifyLogin()
        if(rp){

            btnCadastrar.visibility = View.GONE
            btnLogin.visibility = View.VISIBLE
        } else {
            btnCadastrar.visibility = View.VISIBLE
            btnLogin.visibility = View.GONE
        }

    }

//    fun setListeners() {
//
//        // ao clicar no botao ACESSAR
//        btnLogin.setOnClickListener {
//
//            var myp = Preference(this)
//            //var gson = Gson()

//            //println("LOGIN " + login)
//            //var userAdmin = gson.fromJson(login, UserAdmin::class.java)
//
//            //println(userAdmin.senha)
//
////            val email = userAdmin.email
////            val senha = userAdmin.senha
////
////            val eEmail = "dariosalles@gmail.com"
////            val eSenha = "789456123"
////
////            if(eEmail == email && eSenha == senha){
////                val intent = Intent(this, SenhasActivity::class.java)
////                startActivity(intent)
////            } else {
////                showToast("Erro ao conectar")
////            }
//
//
//        }
//        // ao clicar no botao CADASTRAR
//        btnCadastrar.setOnClickListener {
//            val intent = Intent(this, CadastroActivity::class.java)
//            startActivity(intent)
//        }
//
//    }


    // verifica se o usuário já existe
//    fun verificaUser() : MutableList<UserAdmin> {
//
//        val list = getInstaceSharedPreferences().getString("useradmin", "[]")
//
//        println("Lista: $list")
//
//        //converte uma Lista em um objeto
//        val formType = object : TypeToken<List<UserAdmin>>() {}.type
//
//        //retorna um objeto
//        return Gson().fromJson(list, formType)
//
//
//    }
//    fun fakeUser(){
//        userAdmin.add(0, UserAdmin("Dario", "admin@admin", "123456"))
//        Log.d(userAdmin.toString(), "User admin")
//
//        getInstaceSharedPreferences().edit() {
//            //transforma o lista de objeto em Json
//            //importacao da dependencia no gradle GSON
//            val json = Gson().toJson(userAdmin)
//            putString("useradmin", json)
//            commit() //grava a persistencia em uma thread separada evitando assim ao consultar os dados que a thread tenha finalizado
//        }
//    }

    // instancia o SharedPreferences
//    private fun getInstaceSharedPreferences() : SharedPreferences {
//        return getSharedPreferences("com.dsxweb.minhassenhas.PREFERENCES", Context.MODE_PRIVATE)
//    }

    // função para controlar as acões

    // função de login
//    fun Login(email: String, senha: String){
//
//        val userAdmin : UserAdmin
//        val list = getInstaceSharedPreferences().getString("useradmin", "[]")
//        val formType = object : TypeToken<List<UserAdmin>>() {}.type
//        return Gson().fromJson(list, formType)
//
//
//    }

}
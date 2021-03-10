package com.dsxweb.minhassenhas.helpers

import com.dsxweb.minhassenhas.feature.listadesenhas.model.ContatosVO

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.dsxweb.minhassenhas.feature.listadesenhas.model.Password

class HelperDB(
        context: Context
) : SQLiteOpenHelper(context, NOME_BANCO, null, VERSAO_ATUAL) {

    companion object {
        private val NOME_BANCO = "senhas.db"
        private val VERSAO_ATUAL = 1
    }

    val TABLE_NAME = "senha"
    val COLUMNS_ID = "id"
    val COLUMNS_LOGIN = "login"
    val COLUMNS_SENHA = "senha"
    val COLUMNS_CATEGORIA = "categoria"
    val COLUMNS_OBS = "obs"

    val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
            "$COLUMNS_ID INTEGER NOT NULL," +
            "$COLUMNS_LOGIN TEXT NOT NULL," +
            "$COLUMNS_SENHA TEXT NOT NULL," +
            "$COLUMNS_CATEGORIA TEXT NOT NULL," +
            "$COLUMNS_OBS TEXT NOT NULL," +
            "" +
            "PRIMARY KEY($COLUMNS_ID AUTOINCREMENT)" +
            ")"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if(oldVersion != newVersion) {
            db?.execSQL(DROP_TABLE)
        }
        onCreate(db)
    }

    fun buscarContatos(busca: String, isBuscaPorID: Boolean = false) : List<Password> {
        val db = readableDatabase ?: return mutableListOf()
        var lista = mutableListOf<Password>()
        var where: String? = null
        var args: Array<String> = arrayOf()
        if(isBuscaPorID){
            where = "$COLUMNS_ID = ?"
            args = arrayOf("$busca")
        }else{
            where = "$COLUMNS_OBS LIKE ?"
            args = arrayOf("%$busca%")
        }
        var cursor = db.query(TABLE_NAME,null,where,args,null,null,null)
        if (cursor == null){
            db.close()
            return mutableListOf()
        }
        while(cursor.moveToNext()){
            var senha = Password(
                    cursor.getInt(cursor.getColumnIndex(COLUMNS_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMNS_LOGIN)),
                    cursor.getString(cursor.getColumnIndex(COLUMNS_SENHA)),
                    cursor.getString(cursor.getColumnIndex(COLUMNS_CATEGORIA)),
                    cursor.getString(cursor.getColumnIndex(COLUMNS_OBS))
            )
            lista.add(senha)
        }
        db.close()
        return lista
    }

    fun salvarContato(senha: Password) {
        val db = writableDatabase ?: return
        var content = ContentValues()
        content.put(COLUMNS_LOGIN,senha.login)
        content.put(COLUMNS_SENHA,senha.senha)
        content.put(COLUMNS_CATEGORIA,senha.categoria)
        db.insert(TABLE_NAME,null,content)
        db.close()
    }

    fun deletarCoontato(id: Int) {
        val db = writableDatabase ?: return
        val sql = "DELETE FROM $TABLE_NAME WHERE $COLUMNS_ID = ?"
        val arg = arrayOf("$id")
        db.execSQL(sql,arg)
        db.close()
    }

    fun updateContato(senha: Password) {
        val db = writableDatabase ?: return
        val sql = "UPDATE $TABLE_NAME SET $COLUMNS_LOGIN = ?, $COLUMNS_SENHA = ?, $COLUMNS_CATEGORIA = ?, $COLUMNS_OBS = ? WHERE $COLUMNS_ID = ?"
        val arg = arrayOf(senha.login,senha.senha,senha.categoria,senha.observacao,senha.id)
        db.execSQL(sql,arg)
        db.close()
    }
}

//import android.content.Context
//import android.database.sqlite.SQLiteDatabase
//import android.database.sqlite.SQLiteOpenHelper
//import com.dsxweb.minhassenhas.feature.listadesenhas.model.Password
//
//class HelperDB(
//        context: Context
//
//) : SQLiteOpenHelper(context, NOME_BANCO, null, VERSAO_ATUAL) {
//
//    companion object {
//        private val NOME_BANCO = "senhas.db"
//        private val VERSAO_ATUAL = 1
//    }
//
//    val TABLE_NAME = "senhas"
//    val COLLUMNS_ID = "id"
//    val COLLUMNS_LOGIN = "login"
//    val COLLUMNS_SENHA = "senha"
//    val COLLUMNS_CATEGORIA = "categoria"
//    val COLLUMNS_OBS = "obs"
//
//    val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
//    val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
//            "$COLLUMNS_ID INTEGER NOT NULL," +
//            "$COLLUMNS_LOGIN TEXT NOT NULL," +
//            "$COLLUMNS_SENHA TEXT NOT NULL," +
//            "$COLLUMNS_LOGIN TEXT NOT NULL," +
//            "$COLLUMNS_CATEGORIA TEXT NOT NULL," +
//            "$COLLUMNS_OBS TEXT NOT NULL," +
//            "" +
//            "PRIMARY KEY ($COLLUMNS_ID AUTOINCREMENT)" +
//            ")"
//
//
//    override fun onCreate(db: SQLiteDatabase?) {
//        // criar  o banco de dados
//        db?.execSQL(CREATE_TABLE)
//    }
//
//    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        if(oldVersion != newVersion){
//            // atualizar bd
//            db?.execSQL(DROP_TABLE)
//            onCreate(db)
//        }
//
//    }
//
//    fun buscarSenhas(busca: String) : List<Password> {
//
//        val db = readableDatabase ?: return mutableListOf()
//        var lista = mutableListOf<Password>()
//        val sql = "SELECT * FROM $TABLE_NAME"
//        var cursor = db.rawQuery(sql, arrayOf()) ?: return mutableListOf()
//
//        while(cursor.moveToNext()){
//
//            var senha = Password(
//                    cursor.getInt(cursor.getColumnIndex(COLLUMNS_ID)),
//                    cursor.getString(cursor.getColumnIndex(COLLUMNS_LOGIN)),
//                    cursor.getString(cursor.getColumnIndex(COLLUMNS_SENHA)),
//                    cursor.getString(cursor.getColumnIndex(COLLUMNS_CATEGORIA)),
//                    cursor.getString(cursor.getColumnIndex(COLLUMNS_OBS)),
//
//            )
//            db.close()
//            lista.add(senha)
//        }
//            return lista
//    }
//
//    fun salvarSenha(senha: Password){
//        val db = writableDatabase ?: return
//        val sql = "INSERT into $TABLE_NAME ($COLLUMNS_LOGIN, $COLLUMNS_SENHA, $COLLUMNS_CATEGORIA, $COLLUMNS_CATEGORIA) VALUES (?,?,?,?)"
//        var array = arrayOf(senha.login,senha.senha,senha.categoria,senha.categoria)
//        db.execSQL(sql, array)
//        db.close()
//    }
//}
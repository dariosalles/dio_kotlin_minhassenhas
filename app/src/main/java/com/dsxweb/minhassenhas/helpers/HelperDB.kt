package com.dsxweb.minhassenhas.helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.dsxweb.minhassenhas.Password

class HelperDB(
        context: Context

) : SQLiteOpenHelper(context, NOME_BANCO, null, VERSAO_ATUAL) {

    companion object {
        private val NOME_BANCO = "senhas.db"
        private val VERSAO_ATUAL = 1
    }

    val TABLE_NAME = "senhas"
    val COLLUMNS_ID = "id"
    val COLLUMNS_LOGIN = "login"
    val COLLUMNS_SENHA = "senha"
    val COLLUMNS_CATEGORIA = "categoria"
    val COLLUMNS_OBS = "obs"

    val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
            "$COLLUMNS_ID INTEGER NOT NULL," +
            "$COLLUMNS_LOGIN TEXT NOT NULL," +
            "$COLLUMNS_SENHA TEXT NOT NULL," +
            "$COLLUMNS_LOGIN TEXT NOT NULL," +
            "$COLLUMNS_CATEGORIA TEXT NOT NULL," +
            "$COLLUMNS_OBS TEXT NOT NULL," +
            "" +
            "PRIMARY KEY ($COLLUMNS_ID AUTOINCREMENT)" +
            ")"


    override fun onCreate(db: SQLiteDatabase?) {
        // criar  o banco de dados
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if(oldVersion != newVersion){
            // atualizar bd
            db?.execSQL(DROP_TABLE)
            onCreate(db)
        }

    }

    fun buscarSenhas(busca: String) : List<Password> {

        val db = readableDatabase ?: return mutableListOf()
        var lista = mutableListOf<Password>()
        val sql = "SELECT * FROM $TABLE_NAME"
        var cursor = db.rawQuery(sql, arrayOf()) ?: return mutableListOf()

        while(cursor.moveToNext()){

            var senha = Password(
                    cursor.getInt(cursor.getColumnIndex(COLLUMNS_ID)),
                    cursor.getString(cursor.getColumnIndex(COLLUMNS_LOGIN)),
                    cursor.getString(cursor.getColumnIndex(COLLUMNS_SENHA)),
                    cursor.getString(cursor.getColumnIndex(COLLUMNS_CATEGORIA)),
                    cursor.getString(cursor.getColumnIndex(COLLUMNS_OBS)),

            )
            lista.add(senha)
        }
            return lista
    }
}
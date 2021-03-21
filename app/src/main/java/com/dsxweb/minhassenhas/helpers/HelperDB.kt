package com.dsxweb.minhassenhas.helpers

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
        private val VERSAO_ATUAL = 3
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

    fun salvarSenha(senha: Password) {
        val db = writableDatabase ?: return
        var content = ContentValues()
        content.put(COLUMNS_LOGIN,senha.login)
        content.put(COLUMNS_SENHA,senha.senha)
        content.put(COLUMNS_CATEGORIA,senha.categoria)
        content.put(COLUMNS_OBS,senha.observacao)
        db.insert(TABLE_NAME,null,content)
        db.close()
    }

    fun deletarSenha(id: Int) {
        val db = writableDatabase ?: return
        val sql = "DELETE FROM $TABLE_NAME WHERE $COLUMNS_ID = ?"
        val arg = arrayOf("$id")
        db.execSQL(sql,arg)
        db.close()
    }

    fun updateSenha(senha: Password) {
        val db = writableDatabase ?: return
        val sql = "UPDATE $TABLE_NAME SET $COLUMNS_LOGIN = ?, $COLUMNS_SENHA = ?, $COLUMNS_CATEGORIA = ?, $COLUMNS_OBS = ? WHERE $COLUMNS_ID = ?"
        val arg = arrayOf(senha.login,senha.senha,senha.categoria,senha.observacao,senha.id)
        db.execSQL(sql,arg)
        db.close()
    }
}
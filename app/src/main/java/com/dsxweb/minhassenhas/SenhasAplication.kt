package com.dsxweb.minhassenhas

import android.app.Application
import com.dsxweb.minhassenhas.helpers.HelperDB

class SenhasAplication : Application() {

    // SINGLETON
    companion object{
        lateinit var instance: SenhasAplication
    }

     var helperDB: HelperDB? = null
        private set //bloqueado para set externos, somente get aceito

    override fun onCreate() {
        super.onCreate()
        instance = this
        helperDB = HelperDB(this)

    }

}
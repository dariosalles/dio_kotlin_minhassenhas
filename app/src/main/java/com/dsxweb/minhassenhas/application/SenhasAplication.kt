package com.dsxweb.minhassenhas.application

import android.app.Application
import com.dsxweb.minhassenhas.helpers.HelperDB

class SenhasAplication : Application() {

    var helperDB: HelperDB? = null
        private set //bloqueado para set externos, somente get aceito

    // SINGLETON
    companion object{
        lateinit var instance: SenhasAplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        helperDB = HelperDB(this)

    }

}
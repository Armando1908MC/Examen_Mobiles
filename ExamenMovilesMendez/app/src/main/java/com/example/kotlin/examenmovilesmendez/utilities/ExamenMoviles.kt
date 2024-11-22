package com.example.kotlin.examenmovilesmendez.utilities

import android.app.Application
import com.example.kotlin.examenmovilesmendez.data.network.NetworkModuleDI
import com.parse.Parse

class ExamenMoviles : Application() {
    override fun onCreate() {
        super.onCreate()
        NetworkModuleDI.initializeParse(this)
    }
}
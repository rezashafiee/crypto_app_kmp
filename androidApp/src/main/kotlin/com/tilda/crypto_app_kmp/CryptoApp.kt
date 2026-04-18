package com.tilda.crypto_app_kmp

import android.app.Application
import com.tilda.crypto_app_kmp.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class CryptoApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@CryptoApp)
            androidLogger()
        }
    }
}


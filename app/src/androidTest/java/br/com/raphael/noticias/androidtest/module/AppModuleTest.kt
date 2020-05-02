package br.com.raphael.noticias.androidtest.module

import android.content.Context
import android.content.SharedPreferences
import androidx.test.platform.app.InstrumentationRegistry
import br.com.raphael.noticias.App
import br.com.raphael.noticias.di.module.AppModule
import java.util.*

class AppModuleTest(app : App): AppModule(app) {

    override fun provideContext(): Context =
        InstrumentationRegistry.getInstrumentation().targetContext.applicationContext

    override fun provideSharedPreferences(): SharedPreferences =
        provideContext().getSharedPreferences(UUID.randomUUID().toString(), 0)
}
package br.com.raphael.noticias.androidtest

import br.com.raphael.noticias.App
import br.com.raphael.noticias.di.AppComponent
import br.com.raphael.noticias.di.DaggerAppComponent
import br.com.raphael.noticias.di.module.AppModule
import br.com.raphael.noticias.di.module.BackendModule
import br.com.raphael.noticias.di.module.RemoteModule

class AppTest : App() {

    override val component: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .backendModule(BackendModule())
            .remoteModule(RemoteModule())
            .build()
    }

}
package br.com.raphael.noticias.di.module

import br.com.raphael.noticias.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import org.jetbrains.anko.defaultSharedPreferences

@Module
open class AppModule (private val app: App) {

    @Provides
    @Singleton
    fun provideApp() = app

    @Provides
    @Singleton
    open fun provideContext() = app.applicationContext!!

    @Provides
    @Singleton
    open fun provideSharedPreferences() = app.defaultSharedPreferences

}
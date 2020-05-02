package br.com.raphael.noticias.androidtest.module

import br.com.raphael.noticias.di.module.BackendModule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import br.com.raphael.noticias.extensions.readJSON
import br.com.raphael.noticias.remote.BackendService
import com.nhaarman.mockitokotlin2.doReturn
import retrofit2.Retrofit
import javax.inject.Singleton

open class BackendModuleTest : BackendModule() {

    // BackendService
    @Singleton
    override fun providesBackendService(retrofit: Retrofit) = mock<BackendService>{
        // postLogin(any, any)
        on {
            runBlocking { postLogin(any(), any()) }
        } doReturn readJSON("login")

        // getDocumentos()
        on {
            runBlocking { getDocumentos() }
        } doReturn readJSON("documentos")

        // getDocumento(any)
        on {
            runBlocking { getDocumentos() }
        } doReturn readJSON("documento")
    }

}
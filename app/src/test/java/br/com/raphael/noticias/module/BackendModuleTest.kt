package br.com.raphael.noticias.module

import br.com.raphael.noticias.di.module.BackendModule
import br.com.raphael.noticias.remote.BackendService
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import dagger.Module
import kotlinx.coroutines.runBlocking
import br.com.raphael.noticias.extensions.readJSON
import com.nhaarman.mockitokotlin2.any
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
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
            runBlocking { getDocumento(any()) }
        } doReturn readJSON("documento")
    }

}
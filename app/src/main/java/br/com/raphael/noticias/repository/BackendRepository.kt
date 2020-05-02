package br.com.raphael.noticias.repository

import android.content.SharedPreferences
import br.com.raphael.noticias.model.Documento
import br.com.raphael.noticias.model.Response
import br.com.raphael.noticias.model.Token
import br.com.raphael.noticias.remote.BackendService
import javax.inject.Inject

class BackendRepository @Inject constructor(
    private val backendService: BackendService,
    private val preferences: SharedPreferences
) {

    suspend fun postLoginAsync(user: String, pass: String): Token {
        return backendService.postLogin(user, pass)
    }

    suspend fun getDocumentosAsync(): List<Documento> {
        return backendService.getDocumentos()
    }

    suspend fun getDocumentoAsync(id: String): List<Response> {
        return backendService.getDocumento(id)
    }
}

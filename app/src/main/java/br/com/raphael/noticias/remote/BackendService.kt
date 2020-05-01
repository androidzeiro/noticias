package br.com.raphael.noticias.remote

import br.com.raphael.noticias.model.Documento
import br.com.raphael.noticias.model.Response
import br.com.raphael.noticias.model.Token
import retrofit2.http.*

interface BackendService {

    @FormUrlEncoded
    @POST("login")
    suspend fun postLogin(
        @Field("user") user: String,
        @Field("pass") password: String
    ): Token

    @GET("news")
    suspend fun getDocumentos(): List<Documento>

    @GET("news/{id}")
    suspend fun getDocumento(
        @Path("id") id: String
    ): Response
}
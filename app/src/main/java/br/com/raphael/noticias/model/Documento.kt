package br.com.raphael.noticias.model

data class Documento(
    val id_documento: String,
    val chapeu: String,
    val titulo: String,
    val linha_fina: String,
    val data_hora_publicacao: String,
    val url: String,
    val imagem: String,
    val source: String
)
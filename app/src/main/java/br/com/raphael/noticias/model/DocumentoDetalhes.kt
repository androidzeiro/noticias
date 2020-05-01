package br.com.raphael.noticias.model

data class DocumentoDetalhes(
    val id: String,
    val url: String,
    val source: String,
    val produto: String,
    val editoria: String,
    val subeditoria: String,
    val titulo: String,
    val credito: String,
    val datapub: String,
    val horapub: String,
    val linhafina: String,
    val imagem: String,
    val thumbnail: String,
    val creditoImagem: String,
    val legendaImagem: String,
    val origem: String,
    val corpoformatado: String
)
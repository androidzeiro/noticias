package br.com.raphael.noticias.viewmodel

import br.com.raphael.noticias.BaseTest
import br.com.raphael.noticias.R
import br.com.raphael.noticias.helpers.FakeHttpException
import com.nhaarman.mockitokotlin2.any
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito

class ListagemViewModelTest : BaseTest() {

    lateinit var listagemViewModel: ListagemViewModel

    @Before
    override fun setUp() {
        super.setUp()

        listagemViewModel = ListagemViewModel(app)
    }

    @Test
    fun `Testar erro getEventos`() = runBlocking {

        // Configurar Mockito
        Mockito.`when`(listagemViewModel.backendRepository.getDocumentosAsync()).thenThrow(
            FakeHttpException()
        )
        Mockito.`when`(listagemViewModel.resources.getString(any())).thenReturn(context.getString(R.string.msg_erro_http))

        listagemViewModel.getDocumentos()

        listagemViewModel.error.observeForever {
            assertEquals(context.getString(R.string.msg_erro_http), it)
        }
    }

    @Test
    fun `Testar retorno getEventos`() = runBlocking {

        listagemViewModel.success.observeForever {
            // empty
            assertTrue(it.isNotEmpty())

            // size
            assertEquals(3, it.size)

            // index 0 - Dados utilizados na listagem
            assertEquals("1", it[0].id_documento)
            assertEquals("Política", it[0].chapeu)
            assertEquals("https://img.estadao.com.br/resources/jpg/2/4/1545959783142.jpg", it[0].imagem)
            assertEquals("Antes de demissão, secretário de Covas enfrentou pressão por segurar gastos de secretarias", it[0].titulo)
        }
    }
}
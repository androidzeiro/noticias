package br.com.raphael.noticias.viewmodel

import br.com.raphael.noticias.BaseTest
import br.com.raphael.noticias.R
import br.com.raphael.noticias.helpers.FakeHttpException
import com.nhaarman.mockitokotlin2.any
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class DetalhesViewModelTest : BaseTest() {

    lateinit var detalhesViewModel: DetalhesViewModel

    @Before
    override fun setUp() {
        super.setUp()

        detalhesViewModel = DetalhesViewModel(app)
    }

    @Test
    fun `Testar retorno getEvento`() = runBlocking {

        detalhesViewModel.getDocumento("0")

        detalhesViewModel.success.observeForever {

            // index 0 - Dados utilizados na listagem
            assertEquals("1", it.id)
            assertEquals("https://img.estadao.com.br/resources/jpg/2/4/1545959783142.jpg", it.imagem)
            assertEquals("Antes de demissão, secretário de Covas enfrentou pressão por segurar gastos de secretarias", it.titulo)
        }
    }

    @Test
    fun `Testar erro getEvento`() = runBlocking {

        // Configurar Mockito
        Mockito.`when`(detalhesViewModel.backendRepository.getDocumentoAsync(any())).thenThrow(
            FakeHttpException()
        )
        Mockito.`when`(detalhesViewModel.resources.getString(any())).thenReturn(context.getString(R.string.msg_erro_http))

        detalhesViewModel.getDocumento("0")

        detalhesViewModel.error.observeForever {
            assertEquals(context.getString(R.string.msg_erro_http), it)
        }
    }
}
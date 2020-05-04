package br.com.raphael.noticias.viewmodel

import br.com.raphael.noticias.BaseTest
import br.com.raphael.noticias.R
import br.com.raphael.noticias.helpers.FakeHttpException
import br.com.raphael.noticias.model.FieldError
import com.nhaarman.mockitokotlin2.any
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class LoginViewModelTest : BaseTest() {

    lateinit var perfilViewModel: LoginViewModel

    @Before
    override fun setUp() {
        super.setUp()

        perfilViewModel = LoginViewModel(app)
    }

    @Test
    fun `Testar login com user e password corretos`() = runBlocking {

        perfilViewModel.setUser("devmobile")
        perfilViewModel.setPassword("uC&+}H4wg?rYbF:")
        perfilViewModel.postLogin()

        perfilViewModel.success.observeForever {
            Assert.assertTrue(it)
        }
    }

    @Test
    fun `Testar login com user e password vazios`() = runBlocking {

        perfilViewModel.setUser("")
        perfilViewModel.setPassword("")
        val result = perfilViewModel.postLogin()

        Assert.assertTrue(result.contains(FieldError(R.id.til_user, R.string.msg_user_obrigatorio)))
        Assert.assertTrue(result.contains(FieldError(R.id.til_password, R.string.msg_password_obrigatorio)))
    }

    @Test
    fun `Testar erro 401 postLogin`() = runBlocking {

        // Configurar Mockito
        Mockito.`when`(perfilViewModel.backendRepository.getDocumentosAsync()).thenThrow(
            FakeHttpException(401)
        )
        Mockito.`when`(perfilViewModel.resources.getString(any())).thenReturn(context.getString(R.string.usuario_senha_invalida))

        perfilViewModel.setUser("1234")
        perfilViewModel.setPassword("1234")
        perfilViewModel.postLogin()

        perfilViewModel.error.observeForever {
            Assert.assertEquals(context.getString(R.string.usuario_senha_invalida), it)
        }
    }

    @Test
    fun `Testar outros erros postLogin`() = runBlocking {

        // Configurar Mockito
        Mockito.`when`(perfilViewModel.backendRepository.getDocumentosAsync()).thenThrow(
            FakeHttpException()
        )
        Mockito.`when`(perfilViewModel.resources.getString(any())).thenReturn(context.getString(R.string.msg_erro_http))

        perfilViewModel.setUser("1234")
        perfilViewModel.setPassword("1234")
        perfilViewModel.postLogin()

        perfilViewModel.error.observeForever {
            Assert.assertEquals(context.getString(R.string.msg_erro_http), it)
        }
    }

}
package br.com.raphael.noticias.viewmodel

import br.com.raphael.noticias.BaseTest
import org.junit.Before
import org.junit.Test

class LoginViewModelTest : BaseTest() {

    lateinit var perfilViewModel: LoginViewModel

    @Before
    override fun setUp() {
        super.setUp()

        perfilViewModel = LoginViewModel(app)
    }

    @Test
    fun `Testar`() {

    }

}
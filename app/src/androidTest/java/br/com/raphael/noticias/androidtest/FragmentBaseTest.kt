package br.com.raphael.noticias.androidtest

import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.launchFragmentInContainer
import br.com.raphael.noticias.R

open class FragmentBaseTest : BaseTest() {

    inline fun <reified T:Fragment>launchFragment() = launchFragmentInContainer<T>(themeResId = R.style.AppTheme)

}

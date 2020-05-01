package br.com.raphael.noticias.di

import br.com.raphael.noticias.App
import br.com.raphael.noticias.di.module.AppModule
import br.com.raphael.noticias.di.module.BackendModule
import br.com.raphael.noticias.di.module.RemoteModule
import br.com.raphael.noticias.MainActivity
import br.com.raphael.noticias.viewmodel.DetalhesViewModel
import br.com.raphael.noticias.viewmodel.ListagemViewModel
import br.com.raphael.noticias.viewmodel.LoginViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        BackendModule::class,
        RemoteModule::class
    ]
)
interface AppComponent {

    fun inject(app: App)

    fun inject(activity: MainActivity)

    fun inject(viewModel: LoginViewModel)
    fun inject(viewModel: ListagemViewModel)
    fun inject(viewModel: DetalhesViewModel)

}
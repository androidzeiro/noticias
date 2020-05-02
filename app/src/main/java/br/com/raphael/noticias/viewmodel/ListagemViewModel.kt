package br.com.raphael.noticias.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.raphael.noticias.App
import br.com.raphael.noticias.model.Documento
import br.com.raphael.noticias.repository.BackendRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListagemViewModel(application: Application) : AndroidViewModel(application) {

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _success = MutableLiveData<List<Documento>>()
    val success: MutableLiveData<List<Documento>>
        get() = _success

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    @Inject
    lateinit var backendRepository: BackendRepository

    init {
        getApplication<App>().component.inject(this)
        getDocumentos()
    }

    fun getDocumentos() {
        viewModelScope.launch {
            try {
                _loading.postValue(true)
                val response = backendRepository.getDocumentosAsync()
                _success.postValue(response)
                _loading.postValue(false)
            } catch (e: Exception) {
                _error.postValue(e.toString())
            }
        }
    }
}

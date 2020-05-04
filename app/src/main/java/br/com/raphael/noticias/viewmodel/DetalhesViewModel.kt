package br.com.raphael.noticias.viewmodel

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.raphael.noticias.App
import br.com.raphael.noticias.R
import br.com.raphael.noticias.model.DocumentoDetalhes
import br.com.raphael.noticias.repository.BackendRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetalhesViewModel(application: Application) : AndroidViewModel(application) {
    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _success = MutableLiveData<DocumentoDetalhes>()
    val success: MutableLiveData<DocumentoDetalhes>
        get() = _success

    @Inject
    lateinit var backendRepository: BackendRepository
    @Inject
    lateinit var resources: Resources

    init {
        getApplication<App>().component.inject(this)
    }

    fun getDocumento(id: String) {
        viewModelScope.launch {
            try {
                val response = backendRepository.getDocumentoAsync(id = id)
                _success.postValue(response[0].documento)
            } catch (e: Exception) {
                _error.postValue(resources.getString(R.string.msg_erro_http))
            }
        }
    }
}

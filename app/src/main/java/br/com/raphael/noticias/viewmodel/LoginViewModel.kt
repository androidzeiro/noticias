package br.com.raphael.noticias.viewmodel

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.raphael.noticias.App
import br.com.raphael.noticias.R
import br.com.raphael.noticias.model.FieldError
import br.com.raphael.noticias.repository.BackendRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _success = MutableLiveData<Boolean>()
    val success: MutableLiveData<Boolean>
        get() = _success

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _user = MutableLiveData<String>()
    val user: LiveData<String>
        get() = _user

    fun setUser(value: String) {
        _user.postValue(value)
    }

    private val _password = MutableLiveData<String>()
    val password: LiveData<String>
        get() = _password

    fun setPassword(value: String) {
        _password.postValue(value)
    }

    @Inject
    lateinit var backendRepository: BackendRepository

    @Inject
    lateinit var preferences: SharedPreferences

    init {
        getApplication<App>().component.inject(this)
    }

    fun postLogin(): List<FieldError> {
        val checkedFields = assertFields()
        if (checkedFields.isEmpty()) {
            viewModelScope.launch {
                try {
                    _loading.postValue(true)
                    backendRepository.postLoginAsync(_user.value!!, _password.value!!)
                    _success.postValue(true)
//                        preferences.edit()
//                            .putString("name", response.userAccount.name)
//                            .putString("agency", response.userAccount.agency)
//                            .putString("bankAccount", response.userAccount.bankAccount)
//                            .apply()

                    _loading.postValue(false)
                } catch (e: Exception) {
                    _error.postValue(e.toString())
                    _loading.postValue(false)
                }
            }
        }

        return checkedFields
    }

    private fun assertFields(): List<FieldError> {
        val fields = arrayListOf<FieldError>()

        // Verificar se User esta vazio
        if (_user.value.isNullOrBlank()) {
            fields.add(FieldError(R.id.til_user, R.string.msg_user_obrigatorio))
        }

        // Verificar se Password esta vazio
        if (_password.value.isNullOrBlank()) {
            fields.add(FieldError(R.id.til_password, R.string.msg_password_obrigatorio))
        }

        return fields
    }
}

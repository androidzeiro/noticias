package br.com.raphael.noticias.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import br.com.raphael.noticias.R
import br.com.raphael.noticias.viewmodel.LoginViewModel
import cn.pedant.SweetAlert.SweetAlertDialog
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observerSuccess()
        observerError()
        observerLoading()

        btn_login.setOnClickListener {
            login()
        }

        til_user.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                viewModel.setUser(s.toString())
            }
        })

        til_password.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                viewModel.setPassword(s.toString())
            }
        })

        tie_password.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                login()
                true
            } else false
        }
    }

    private fun observerSuccess() {
        viewModel.success.observe(viewLifecycleOwner, Observer {
            val action =
                LoginFragmentDirections.actionLoginFragmentToListagemFragment()
            view?.findNavController()?.navigate(action)
        })
    }

    private fun observerError() {
        viewModel.error.observe(viewLifecycleOwner, Observer {
            it?.let {
                SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(getString(R.string.atencao))
                    .setContentText(it)
                    .setConfirmText(getString(R.string.ok))
                    .show()

                btn_login.isVisible = true
            }
        })
    }

    private fun observerLoading() {
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            pb_loading.isVisible = it
        })
    }

    private fun login(){
        btn_login.isVisible = false
        viewModel.postLogin().forEach { fieldError ->
            btn_login.isVisible = true
            when (fieldError.fieldId) {
                R.id.til_user -> {
                    tie_user.error = getString(fieldError.errorStringResourceId)
                }
                R.id.til_password -> {
                    tie_password.error = getString(fieldError.errorStringResourceId)
                }
            }
        }
    }
}

package br.com.raphael.noticias.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import br.com.raphael.noticias.R
import br.com.raphael.noticias.viewmodel.DetalhesViewModel
import cn.pedant.SweetAlert.SweetAlertDialog

class DetalhesFragment : Fragment() {

    private val viewModel: DetalhesViewModel by viewModels()
    private val args: DetalhesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detalhes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerSuccess()
        observerError()
        observerLoading()

        viewModel.getDocumento(args.id)
    }

    private fun observerSuccess() {
        viewModel.success.observe(viewLifecycleOwner, Observer {

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
            }
        })
    }

    private fun observerLoading() {
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            //pb_carregando.isVisible = it
        })
    }
}
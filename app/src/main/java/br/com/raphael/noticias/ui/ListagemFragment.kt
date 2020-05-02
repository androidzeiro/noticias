package br.com.raphael.noticias.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import br.com.raphael.noticias.R
import br.com.raphael.noticias.model.Documento
import br.com.raphael.noticias.ui.adapters.DocumentosAdapter
import br.com.raphael.noticias.viewmodel.ListagemViewModel
import cn.pedant.SweetAlert.SweetAlertDialog
import kotlinx.android.synthetic.main.fragment_listagem.*

class ListagemFragment : Fragment() {

    private val adapter by lazy {
        DocumentosAdapter(
            mutableListOf()
        ) { onDocumentoClicked(it) }
    }

    private val viewModel: ListagemViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_listagem, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupList()

        observerSuccess()
        observerError()
        observerLoading()
    }

    private fun onDocumentoClicked(item: Documento) {
        val action = ListagemFragmentDirections.actionListagemFragmentToDetalhesFragment(id = item.id_documento)
        view?.findNavController()?.navigate(action)
    }

    private fun setupList(){
        rv_listagem.apply {
            rv_listagem.apply {
                layoutManager = LinearLayoutManager(this@ListagemFragment.activity, LinearLayoutManager.VERTICAL,false)
                adapter = this@ListagemFragment.adapter
            }
        }
    }

    private fun observerSuccess() {
        viewModel.success.observe(viewLifecycleOwner, Observer {
                adapter.items.clear()
                adapter.items.addAll(it?: mutableListOf())
                adapter.notifyDataSetChanged()
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
            pb_carregando.isVisible = it
        })
    }

}
package br.com.raphael.noticias.ui

import android.graphics.Color
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
import coil.api.load
import coil.size.Scale
import kotlinx.android.synthetic.main.fragment_detalhes.*

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
            if (it.imagem.isNotEmpty()) {
                iv_capa.load(it.imagem) {
                    crossfade(750)
                    placeholder(R.drawable.ic_image_placeholder)
                    error(R.drawable.ic_image_placeholder)
                    scale(Scale.FILL)
                }
            }

            tv_titulo.text = it.titulo

            val wedData: String =  it.corpoformatado
            val mimeType = "text/html"
            val utfType = "UTF-8"
            tv_descricao.loadData(dataCSS(it.corpoformatado), "text/html", "ISO-8859-1")
            tv_descricao.setBackgroundColor(Color.TRANSPARENT)
        })
    }

    fun dataCSS(content: String) : String {

        var resultado = """<style>
                html, body {
                    overflow-x: hidden !important;
                  }
                p {
                    text-align: left !important;
                    line-height:25px!important;
                  }
                div {
                   text-align: left !important;
                   line-height:25px!important;
                }
                </style>""" +
                content

        resultado = resultado.replace("<br>", "")
        resultado = resultado.replace("</br>", "")

        return resultado
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
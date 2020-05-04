package br.com.raphael.noticias.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.raphael.noticias.R
import br.com.raphael.noticias.model.Documento
import coil.api.load
import coil.size.Scale
import kotlinx.android.synthetic.main.item_noticia.view.*

class DocumentosAdapter(
    var items: MutableList<Documento>,
    private val onDocumentoClicked: (Documento) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_noticia, parent, false)
        return DocumentosViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DocumentosViewHolder) {
            val item = items[position]

            if (item.imagem.isNotEmpty()) {
                holder.imagem.load(item.imagem) {
                    crossfade(750)
                    placeholder(R.drawable.ic_image_placeholder)
                    error(R.drawable.ic_image_placeholder)
                    scale(Scale.FILL)
                }
            }

            holder.chapeu.text = item.chapeu
            holder.titulo.text = item.titulo

            holder.container.setOnClickListener {
                onDocumentoClicked.invoke(item)
            }

        }
    }

    private class DocumentosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val container: LinearLayout by lazy { itemView.cl_container }
        val imagem: ImageView by lazy { itemView.iv_capa }
        val chapeu: TextView by lazy { itemView.tv_chapeu }
        val titulo: TextView by lazy { itemView.tv_titulo }
    }
}
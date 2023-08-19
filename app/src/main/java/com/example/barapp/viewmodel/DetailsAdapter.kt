package com.example.barapp.viewmodel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.barapp.model.DetailsData
import com.example.barapp.databinding.ItemDetailsBinding

class DetailsAdapter(private var detailsList: List<DetailsData>,
                     private val clickListener: OnClickListenerItem
) : RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDetailsBinding.inflate(inflater, parent, false)
        return DetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        val currentItem = detailsList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return detailsList.size
    }

    fun updateData(newDetailsList: List<DetailsData>) {
        detailsList = newDetailsList
        notifyDataSetChanged()
    }

    inner class DetailsViewHolder(private val binding: ItemDetailsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(detailsData: DetailsData) {
            binding.tvProductName.text ="Producto: ${detailsData.descripcion}"
            binding.tvCantidad.text = "Cantidad: ${detailsData.cantidad.toString()}"
            binding.tvPrecio.text = "$ ${detailsData.precio.toString()}"

            binding.root.setOnLongClickListener { clickListener.onItemLongClick(detailsData)
                true }

        }
    }
}


package com.example.barapp.viewmodel

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.barapp.R
import com.example.barapp.view.TablesFragment
import com.example.barapp.databinding.ItemTablesBinding
import com.example.barapp.model.TableData

class TablesAdapter(private var tables: MutableList<TableData>,
                    private var listener: OnClickListenerTable,
                    private var context: Context,
                    private var viewModel: TablesViewModel,
                    private var fragment: TablesFragment

    ): RecyclerView.Adapter<TablesAdapter.ViewHolder>(){


    private lateinit var mContext: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context

        val view = LayoutInflater.from(mContext).inflate(R.layout.item_tables,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int =tables.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val table = tables.get(position)

        with(holder){
            setListener(table)

            binding.tvTableName.text = table.name
            if (table.status == "Disponible"){
                val verde=ContextCompat.getColor(context, R.color.verde)
                binding.tvPrecioTotal.setTextColor(Color.WHITE)
                binding.tvTableName.setBackgroundColor(verde)
            }else{
                val rojo=ContextCompat.getColor(context, R.color.rojo)
                binding.tvPrecioTotal.setTextColor(Color.BLACK)
                binding.tvTableName.setBackgroundColor(rojo)
            }
            binding.tvPrecioTotal.text ="Valor total en ${table.name} : $ ${table.total.toString()}"
            binding.tvState.text = "Estado de la mesa: ${table.status}"

        }


    }

    inner class ViewHolder( view : View): RecyclerView.ViewHolder(view){
        val binding = ItemTablesBinding.bind(view)

        fun setListener(tableData: TableData){
            with(binding.root){
                setOnClickListener { listener.onClick(tableData.id) }

                setOnLongClickListener {

                    showOptionsDialog(tableData)
                    true
                }
            }
        }

        private fun showOptionsDialog(tableData: TableData) {
            val options = arrayOf("Limpiar", "Eliminar")
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Opciones")
            builder.setItems(options) { _, which ->
                when (which) {
                    0 -> listener.onClearTable(tableData)
                    1 -> listener.onDeleteTable(tableData)
                }
            }
            builder.setNegativeButton("Cancelar", null)
            builder.show()
        }

    }

    fun setViewModel(viewModel: TablesViewModel) {
        this.viewModel = viewModel
    }

    fun getTablesCount(): Int {
        return tables.size
    }

    fun updateData(newTables: List<TableData>) {
        tables.clear()
        tables.addAll(newTables)
        notifyDataSetChanged()

        val tablesCount = getTablesCount()
        val message = if (tablesCount == 1) {
            "Tienes 1 Mesa registrada"
        } else {
            "Tienes $tablesCount Mesas registradas"
        }
        fragment.updateTablesCount(message)

    }

}
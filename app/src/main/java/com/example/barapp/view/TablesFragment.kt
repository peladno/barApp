package com.example.barapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.barapp.R
import com.example.barapp.viewmodel.TablesViewModel
import com.example.barapp.databinding.FragmentTablesBinding
import com.example.barapp.model.TableData
import com.example.barapp.viewmodel.OnClickListenerTable
import com.example.barapp.viewmodel.TablesAdapter
import kotlinx.coroutines.launch

class TablesFragment : Fragment(), OnClickListenerTable {

    private lateinit var mBinding: FragmentTablesBinding
    private val viewModel: TablesViewModel by viewModels()
    private lateinit var adapter: TablesAdapter




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentTablesBinding.inflate(inflater, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TablesAdapter(mutableListOf(), this,requireContext(),viewModel, this)

        mBinding.rvTables.layoutManager = LinearLayoutManager(requireContext())
        mBinding.rvTables.adapter = adapter

        viewModel.getAllTables().observe(viewLifecycleOwner) { tablesList ->
            adapter.updateData(tablesList)
        }



        mBinding.fabAddTable.setOnClickListener { loadAddTableFragment() }

        mBinding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_item_addTable ->{
                    loadAddTableFragment()
                    true
                }
                R.id.menu_item_exit -> {
                    showExitConfirmationDialog()
                    true
                }
                else -> false
            }
        }

    }
    private fun showExitConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Confirmación")
        builder.setMessage("¿Estás seguro de que deseas salir?")
        builder.setPositiveButton("Sí") { dialog, _ ->

            requireActivity().finish()
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ ->

            dialog.dismiss()
        }
        builder.show()
    }

    fun updateTablesCount(message: String) {
        mBinding.tvTitle.text = message
    }

    private fun loadAddTableFragment() {
        val addTableFragment = AddTableFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frame, addTableFragment)
            .addToBackStack(null)
            .commit()
    }


    override fun onClick(tableId: Int) {

         val detailstablefragment = DetailsTableFragment()
         val bundle = Bundle()
         bundle.putInt("tableId", tableId)
        detailstablefragment.arguments = bundle

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frame, detailstablefragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDeleteTable(tableData: TableData) {
        showDeleteConfirmationDialog(tableData)
    }

    override fun onLongClick(tableData: TableData) {
        val options = arrayOf("Limpiar", "Eliminar")
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Opciones")
        builder.setItems(options) { dialog, which ->
            when (which) {
                0 -> onClearTable(tableData)
                1 -> showDeleteConfirmationDialog(tableData)
            }
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    override fun onClearTable(tableData: TableData) {
        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle("Limpiar ${tableData.name}")
            .setMessage("¿Estás seguro de que deseas limpiar todos los items de esta mesa?")
            .setPositiveButton("Limpiar") { _, _ ->

                lifecycleScope.launch { viewModel.cleanTable(tableData.id) }
            }
            .setNegativeButton("Cancelar", null)
            .create()

        alertDialog.show()
    }



    private fun showDeleteConfirmationDialog(tableData: TableData) {
        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle("Eliminar ${tableData.name}")
            .setMessage("¿Estás seguro de que deseas eliminar esta mesa?")
            .setPositiveButton("Eliminar") { _, _ ->
                viewModel.cleanTable(tableData.id)
                viewModel.deleteTable(tableData)
            }
            .setNegativeButton("Cancelar", null)
            .create()

        alertDialog.show()
    }
}

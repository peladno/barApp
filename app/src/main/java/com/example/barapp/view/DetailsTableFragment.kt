package com.example.barapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.barapp.model.DetailsData
import com.example.barapp.viewmodel.OnClickListenerItem
import com.example.barapp.R
import com.example.barapp.model.TableData
import com.example.barapp.viewmodel.TablesViewModel
import com.example.barapp.databinding.FragmentDetailsTableBinding
import com.example.barapp.viewmodel.DetailsAdapter
import kotlinx.coroutines.launch


class DetailsTableFragment : Fragment(), OnClickListenerItem {

    private lateinit var mBinding : FragmentDetailsTableBinding
    private val viewModel: TablesViewModel by viewModels()
    private var table: TableData? = null
    private var items: DetailsData? = null
    private lateinit var detailsAdapter: DetailsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        mBinding = FragmentDetailsTableBinding.inflate(inflater,container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        val tableId = args?.getInt("tableId", - 1)?: -1

        lifecycleScope.launch {
             table = viewModel.getOneTable(tableId)
            if (table != null){
                //Toast.makeText(context, "Entrando a ${table!!.name}", Toast.LENGTH_SHORT).show()
                mBinding.toolbar.title = " Estas en: ${table!!.name}"
            }else{
                Toast.makeText(context, "Mesa no Encontrada", Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
        detailsAdapter = DetailsAdapter(emptyList(), this)
        mBinding.rvItem.layoutManager = LinearLayoutManager(requireContext())
        mBinding.rvItem.adapter = detailsAdapter
        mBinding.toolbar.menu.clear()
        mBinding.toolbar.menu.add("Crear Item")
            .setOnMenuItemClickListener {
                addItem()
                true
            }

        mBinding.toolbar.menu.add("Salir")
            .setOnMenuItemClickListener {
                showExitConfirmationDialog()
                true
            }



       viewModel.getDetailsForTable(tableId).observe(viewLifecycleOwner){ detailsList ->
           val adapter = DetailsAdapter(detailsList,this)
           mBinding.rvItem.adapter = adapter
        }

        mBinding.fabAddItem.setOnClickListener {

         addItem()
        }

        detailsAdapter = DetailsAdapter(emptyList(),this)
        mBinding.rvItem.layoutManager = LinearLayoutManager(requireContext())
        mBinding.rvItem.adapter = detailsAdapter

        viewModel.getDetailsForTable(tableId).observe(viewLifecycleOwner, Observer { detailsList ->
            detailsAdapter.updateData(detailsList)
        })

        mBinding.fabGoBack.setOnClickListener { showExitConfirmationDialog() }

    }

    private fun addItem(){
        val tableId = arguments?.getInt("tableId", -1) ?: -1
        val addItemFragment = AddItemTableFragment()
        val bundle = Bundle()
        bundle.putInt("tableId", tableId)
        addItemFragment.arguments = bundle

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frame, addItemFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun showExitConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Confirmación")
        builder.setMessage("¿Estás seguro de que deseas salir?")
        builder.setPositiveButton("Sí") { dialog, _ ->

            requireActivity().onBackPressedDispatcher.onBackPressed()
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ ->

            dialog.dismiss()
        }
        builder.show()
    }

    override fun onItemClick(item: DetailsData) {

    }

    override fun onItemLongClick(item: DetailsData) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Eliminar item")
        builder.setMessage("¿Estás seguro de que deseas eliminar este item de la mesa?")
        builder.setPositiveButton("Sí") { dialog, _ ->

                viewModel.deleteItem(item)
               dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }


}
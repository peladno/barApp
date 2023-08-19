package com.example.barapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.barapp.databinding.FragmentAddItemTableBinding
import com.example.barapp.viewmodel.TablesViewModel

class AddItemTableFragment : Fragment() {

    private lateinit var mBinding: FragmentAddItemTableBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentAddItemTableBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.btnSave.setOnClickListener {
            if (validateFields()) {
                val descripcion = mBinding.etName.text.toString()
                val cantidad = mBinding.etCantidad.text.toString().toIntOrNull()
                val precio = mBinding.etPrice.text.toString().toIntOrNull()

                val viewModel = ViewModelProvider(requireActivity()).get(TablesViewModel::class.java)
                val tableId = arguments?.getInt("tableId", -1) ?: -1
                viewModel.addNewItemToTable(tableId, descripcion, cantidad!!, precio!!)

                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }

        mBinding.btnExit.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

    }

    private fun validateFields(): Boolean {

        val descripcion = mBinding.etName.text.toString()
        if (descripcion.isEmpty()) {
            mBinding.tilName.error = "Campo Obligatorio"
            mBinding.etName.requestFocus()
            return false
        }

        val cantidad = mBinding.etCantidad.text.toString().toIntOrNull()
        if (cantidad == null) {
            mBinding.tilCantidad.error = "Ingresa una cantidad válida"
            mBinding.etCantidad.requestFocus()
            return false
        }

        val precio = mBinding.etPrice.text.toString().toIntOrNull()
        if (precio == null) {
            mBinding.tilPrice.error = "Ingresa un precio válido"
            mBinding.etPrice.requestFocus()
            return false
        }

        mBinding.tilName.error = null
        mBinding.tilCantidad.error = null
        mBinding.tilPrice.error = null
        return true
    }
}

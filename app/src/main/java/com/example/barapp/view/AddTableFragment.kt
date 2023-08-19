package com.example.barapp.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.barapp.model.TableData
import com.example.barapp.viewmodel.TablesViewModel
import com.example.barapp.databinding.FragmentAddTableBinding


class AddTableFragment : Fragment() {
    private lateinit var mBinding: FragmentAddTableBinding
    private var hasUnsavedChanges = false
    private val viewModel: TablesViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentAddTableBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.btnSave.setOnClickListener {saveTable() }

        mBinding.etName.doAfterTextChanged {
            mBinding.tilName.error = null
            hasUnsavedChanges = true
        }

        mBinding.btnExit.setOnClickListener { showDialogExit()  }

    }

    private fun hideKeyboard() {
        val inputMethodManager = mBinding.root.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        val view = requireActivity().currentFocus
        view?.let {
            inputMethodManager?.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }



    private fun showDialogExit() {
        hideKeyboard()
        if (hasUnsavedChanges) {
            AlertDialog.Builder(requireContext())
                .setTitle("Cambios sin guardar")
                .setMessage("Tienes cambios sin guardar que se perderan. ¿Estás seguro de que deseas salir?")
                .setPositiveButton("Salir") { _, _ ->
                    requireActivity().supportFragmentManager.popBackStack()
                }
                .setNegativeButton("Cancelar", null)
                .setOnDismissListener {
                    hasUnsavedChanges = false
                }
                .show()
        } else {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }


    fun saveTable() {
        val tableName = mBinding.etName.text?.toString()?.trim()
        hasUnsavedChanges = false
        if (tableName.isNullOrEmpty()){
            mBinding.tilName.error = "Campo Obligatorio"
            mBinding.etName.requestFocus()
            return
        }else if (!tableName.matches(Regex("^[A-Za-z0-9 ]+\$"))){
            mBinding.tilName.error = "Nombre solo debe contener letras y números"
            mBinding.etName.requestFocus()
            return
        }


        val newTable = TableData(0,tableName,"Disponible",0)
        viewModel.insertTable(newTable)
        hideKeyboard()
        mBinding.etName.setText("")
        Toast.makeText(context, "Mesa Agregada con Exito", Toast.LENGTH_SHORT).show()
    }

}
package com.example.barapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.barapp.model.DetailsData
import com.example.barapp.model.TableData
import com.example.barapp.model.TableDatabase
import com.example.barapp.model.TableRepository
import kotlinx.coroutines.launch

class TablesViewModel(application: Application) : AndroidViewModel(application) {
    private val tableRepository: TableRepository =
        TableRepository(TableDatabase.getDatabase(application).tableDataDao())


    fun deleteItem(item: DetailsData) {
        viewModelScope.launch {
            val table = tableRepository.getOneTable(item.tableID)
            if (table != null) {
                table.total -= item.subTotal
                if (table.total <= 0) {
                    table.total = 0
                    table.status = "Disponible"
                }
                tableRepository.updateTable(table)
                tableRepository.deleteItem(item)
            }
        }
    }


    fun insertTable(table: TableData) {
        viewModelScope.launch { tableRepository.insertTable(table) }
    }

    fun getAllTables(): LiveData<List<TableData>> {
        return tableRepository.getAllTables()
    }

    fun getDetailsForTable(tableId: Int): LiveData<List<DetailsData>> {
        return tableRepository.getDetailsTable(tableId)
    }

    fun deleteTable(table: TableData){
        viewModelScope.launch {
            tableRepository.deleteTable(table)
        }
    }

   suspend fun getOneTable(id: Int): TableData?{
        return tableRepository.getOneTable(id)
    }

    fun addNewItemToTable(tableId: Int, descripcion: String, cantidad: Int, precio: Int) {
        val newItem = DetailsData(tableID = tableId, descripcion = descripcion, cantidad = cantidad, precio = precio, subTotal = cantidad * precio)
        viewModelScope.launch {

            tableRepository.insertItem(newItem)

            val table = tableRepository.getOneTable(tableId)
            if (table != null) {
                table.status = "Ocupada"
                table.total += newItem.subTotal
                tableRepository.updateTable(table)
            }
        }
    }

     fun cleanTable(id: Int){
         viewModelScope.launch {
             var table : TableData? = tableRepository.getOneTable(id)
             tableRepository.cleanTable(id)
                if (table != null) {
                    table.status = "Disponible"
                    table.total = 0
                    tableRepository.updateTable(table)
                }
         }


    }

  }

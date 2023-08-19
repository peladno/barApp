package com.example.barapp.model

import androidx.lifecycle.LiveData
import com.example.barapp.model.DetailsData
import com.example.barapp.model.TableDao
import com.example.barapp.model.TableData

class TableRepository(private val tableDao: TableDao) {

   suspend fun cleanTable(id: Int){
        tableDao.cleanTable(id)
    }

    suspend fun insertTable(table: TableData) {
        tableDao.insertTable(table)
    }

    suspend fun insertItem(item : DetailsData){
        tableDao.insertItem(item)
    }

    suspend fun updateTable(table: TableData) {
        tableDao.updateTable(table)
    }

    suspend fun updateItem(item: DetailsData) {
        tableDao.updateItem(item)
    }

    suspend fun deleteItem(item: DetailsData) {
        tableDao.deleteItem(item)
    }

    suspend fun deleteTable(table: TableData) {
        tableDao.deleteTable(table)
    }

    fun getAllTables(): LiveData<List<TableData>> {
        return tableDao.getAllTables()
    }

    fun getDetailsTable(id: Int): LiveData<List<DetailsData>> {
        return tableDao.getDetailsTable(id)
    }

   suspend fun getOneTable(id: Int) : TableData? {
        return tableDao.getOneTable(id)
    }


}
package com.example.barapp.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TableDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTable(table: TableData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item : DetailsData)

    @Update
    suspend fun updateTable(table: TableData)

    @Update
    suspend fun updateItem(item : DetailsData)

    @Delete
    suspend fun deleteItem(item : DetailsData)

    @Delete
    suspend fun deleteTable(table : TableData)

    @Query("SELECT * FROM TABLE_DATA ORDER BY name")
    fun getAllTables():LiveData<List<TableData>>

    @Query("SELECT * FROM DETAILS_DATA WHERE tableID = :tableId")
    fun getDetailsTable(tableId:Int):LiveData<List<DetailsData>>

    @Query("SELECT * FROM table_data WHERE id = :tableId")
    suspend fun getOneTable(tableId:Int): TableData?

    @Query("DELETE FROM DETAILS_DATA WHERE tableID = :id")
   suspend fun cleanTable(id:Int) : Int



}
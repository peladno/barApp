package com.example.barapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_data")
data class TableData(
    @PrimaryKey(autoGenerate = true)
    val id : Int =0,
    val name : String,
    var status : String,
    var total : Int,


)

@Entity(tableName = "details_data")
data class DetailsData(
    @PrimaryKey(autoGenerate = true)
    val id : Int= 0,
    val tableID: Int,
    var descripcion: String,
    var cantidad:Int,
    var precio:Int,
    var subTotal: Int

)

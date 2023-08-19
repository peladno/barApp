package com.example.barapp.viewmodel

import com.example.barapp.model.TableData

interface OnClickListenerTable {
    fun onClick(tableId: Int)
    fun onDeleteTable(tableData: TableData)
    fun onLongClick(tableData: TableData)
    fun onClearTable(tableData: TableData)
}
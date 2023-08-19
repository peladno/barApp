package com.example.barapp.viewmodel

import com.example.barapp.model.DetailsData

interface OnClickListenerItem {

    fun onItemClick(item: DetailsData)
    fun onItemLongClick(item: DetailsData)
}
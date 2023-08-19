package com.example.barapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.barapp.viewmodel.OnClickListenerTable
import com.example.barapp.model.TableData
import com.example.barapp.viewmodel.TablesAdapter
import com.example.barapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnClickListenerTable {

    private lateinit var mBinding : ActivityMainBinding
    private lateinit var mGridLayout : GridLayoutManager
    private lateinit var mAdapter : TablesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        launchFragment()
    }

    private fun launchFragment() {
        supportFragmentManager.beginTransaction()
            .replace(mBinding.frame.id, TablesFragment())
            .addToBackStack(null)
            .commit()
    }


    override fun onClick(tableId: Int) {
        TODO("Not yet implemented")
    }

    override fun onDeleteTable(tableData: TableData) {
        TODO("Not yet implemented")
    }

    override fun onLongClick(tableData: TableData) {
        TODO("Not yet implemented")
    }

    override fun onClearTable(tableData: TableData) {
        TODO("Not yet implemented")
    }
}
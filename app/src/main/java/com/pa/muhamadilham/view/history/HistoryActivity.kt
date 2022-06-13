package com.pa.muhamadilham.view.history

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.pa.muhamadilham.view.history.HistoryAdapter
import com.muhamadilham.model.ModelDatabase
import com.pa.muhamadilham.R
import com.pa.muhamadilham.view.absen.AbsenActivity
import com.pa.muhamadilham.view.history.HistoryAdapter.HistoryAdapterCallback
import com.pa.muhamadilham.viewmodel.HistoryViewModel
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.list_history_absen.*

class HistoryActivity : AppCompatActivity(), HistoryAdapter.HistoryAdapterCallback {
    var modelDatabaseList: MutableList<ModelDatabase> = ArrayList()
    lateinit var historyAdapter: HistoryAdapter
    lateinit var historyViewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        btnShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND

                val logData1 = tvNomor.text.toString()
                val logData2 = tvNama.text.toString()
                val logData3 = tvLokasi.text.toString()
                val logData4 = tvAbsenTime.text.toString()
                putExtra(Intent.EXTRA_TEXT,"Nomor : $logData1\n Nama :$logData2\n Lokasi: $logData3 Waktu Absen :$logData4")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)

        }

        setInitLayout()
        setViewModel()
    }

    private fun setInitLayout() {
        tvNotFound.visibility = View.GONE

        historyAdapter = HistoryAdapter(this, modelDatabaseList, this)
        rvHistory.setHasFixedSize(true)
        rvHistory.layoutManager = LinearLayoutManager(this)
        rvHistory.adapter = historyAdapter
    }

    private fun setViewModel() {
        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel::class.java)
        historyViewModel.dataLaporan.observe(this) { modelDatabases: List<ModelDatabase> ->
            if (modelDatabases.isEmpty()) {
                tvNotFound.visibility = View.VISIBLE
                rvHistory.visibility = View.GONE
            } else {
                tvNotFound.visibility = View.GONE
                rvHistory.visibility = View.VISIBLE
            }
            historyAdapter.setDataAdapter(modelDatabases)
        }
    }

    override fun onDelete(modelDatabase: ModelDatabase?) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("Hapus riwayat ini?")
        alertDialogBuilder.setPositiveButton("Hapus") { dialogInterface, i ->
            val uid = modelDatabase!!.uid
            historyViewModel.deleteDataById(uid)

        }
        alertDialogBuilder.setNegativeButton("Batal") { dialogInterface: DialogInterface, i:
        Int -> dialogInterface.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
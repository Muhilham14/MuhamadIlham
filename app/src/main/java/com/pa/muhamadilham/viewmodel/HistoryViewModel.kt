package com.pa.muhamadilham.viewmodel

import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.muhamadilham.model.ModelDatabase
import com.pa.muhamadilham.R
import com.pa.muhamadilham.database.DatabaseClient.Companion.getInstance
import com.pa.muhamadilham.database.dao.DatabaseDao
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers


class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    var dataLaporan: LiveData<List<ModelDatabase>>
    var databaseDao: DatabaseDao? = getInstance(application)?.appDatabase?.databaseDao()

    fun deleteDataById(uid: Int) {
        Completable.fromAction {
            databaseDao?.deleteHistoryById(uid)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }


    init {
        dataLaporan = databaseDao!!.getAllHistory()
    }
}
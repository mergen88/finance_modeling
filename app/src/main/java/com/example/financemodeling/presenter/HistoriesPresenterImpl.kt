package com.example.financemodeling.presenter

import com.example.financemodeling.App
import com.example.financemodeling.api.interfaces.Repo
import com.example.financemodeling.views.HistoriesView
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class HistoriesPresenterImpl: HistoriesPresenter {

    @Inject lateinit var repo: Repo
    var historiesView: HistoriesView? = null

    init {
        App.component.inject(this)
    }

    override fun injectView(view: HistoriesView) {
        this.historiesView = view
    }
    override fun loadHistories(symbol: String, from: Date, to: Date) {
        historiesView?.showProgress()
        val format = SimpleDateFormat("yyyy-MM-dd")
        val from = format.format(from)
        val to = format.format(to)
        repo.getHistories(symbol, from, to) { histories ->
            historiesView?.hideProgress()
            histories?.historical?.let {
                historyList -> historiesView?.onHistories(historyList)
                return@getHistories
            }
            historiesView?.onError()
        }
    }

    override fun onPause() {
        this.historiesView = null
    }
}
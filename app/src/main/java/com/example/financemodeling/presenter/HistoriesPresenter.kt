package com.example.financemodeling.presenter

import com.example.financemodeling.views.HistoriesView
import java.util.*

interface HistoriesPresenter: BasePresenter {

    fun loadHistories(symbol: String, from: Date, to: Date)
    fun injectView(view: HistoriesView)
}
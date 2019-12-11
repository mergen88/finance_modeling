package com.example.financemodeling.views

import com.example.financemodeling.models.History

interface HistoriesView: BaseView {

    fun onHistories(historyList: List<History>)
    fun initButtons()
    fun loadData(pos: Int)
    fun initGraph()
}
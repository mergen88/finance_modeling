package com.example.financemodeling.interfaces

import androidx.lifecycle.LifecycleObserver
import java.util.*

interface Presenter: LifecycleObserver {

    fun injectView(viewContract: ViewContract)
    fun loadCompanies()
    fun loadHistory(symbol: String, from: Date, to: Date)
}
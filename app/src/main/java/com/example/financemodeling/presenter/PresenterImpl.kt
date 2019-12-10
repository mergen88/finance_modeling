package com.example.financemodeling.presenter

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.example.financemodeling.api.RepoImpl
import com.example.financemodeling.interfaces.Presenter
import com.example.financemodeling.interfaces.Repo
import com.example.financemodeling.interfaces.ViewContract
import java.util.*


class PresenterImpl: Presenter {

    private val repo: Repo = RepoImpl()
    private var viewContract: ViewContract? = null

    override fun injectView(viewContract: ViewContract) {
        this.viewContract = viewContract
    }

    override fun loadCompanies() {
        repo.getCompanies { companies ->
            companies?.symbolsList?.let {
                viewContract?.onCompanies(it)
                return@getCompanies
            }
            viewContract?.onLoadError()
        }
    }

    override fun loadHistory(symbol: String, from: Date, to: Date) {
        repo.getHistories(symbol, from, to){
            histories ->
            histories?.historical?.let {
                viewContract?.onHistory(it)
                return@getHistories
            }
            viewContract?.onLoadError()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause(lifecycleOwner: LifecycleOwner) {
        this.viewContract = null
        lifecycleOwner.lifecycle.removeObserver(this)
    }


}
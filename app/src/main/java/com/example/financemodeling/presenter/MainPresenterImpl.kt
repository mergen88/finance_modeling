package com.example.financemodeling.presenter

import com.example.financemodeling.api.RepoImpl
import com.example.financemodeling.api.interfaces.Repo
import com.example.financemodeling.views.MainView


class MainPresenterImpl: MainPresenter {

    val repo: Repo = RepoImpl()
    private var mainView: MainView? = null

    override fun injectView(mainView: MainView) {
        this.mainView = mainView
    }

    override fun onPause() {
        this.mainView = null
    }
}
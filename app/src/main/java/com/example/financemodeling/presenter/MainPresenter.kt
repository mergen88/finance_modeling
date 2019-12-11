package com.example.financemodeling.presenter

import com.example.financemodeling.views.MainView

interface MainPresenter: BasePresenter {

    fun injectView(mainView: MainView)
}
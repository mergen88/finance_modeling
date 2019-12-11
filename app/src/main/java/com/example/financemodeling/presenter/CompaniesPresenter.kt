package com.example.financemodeling.presenter

import com.example.financemodeling.views.CompaniesView

interface CompaniesPresenter: BasePresenter {

    fun loadCompanies()
    fun injectView(view: CompaniesView)
}
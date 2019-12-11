package com.example.financemodeling.presenter

import com.example.financemodeling.App
import com.example.financemodeling.api.interfaces.Repo
import com.example.financemodeling.views.CompaniesView
import javax.inject.Inject

class CompaniesPresenterImpl: CompaniesPresenter {

    @Inject lateinit var repo: Repo
    var companiesView: CompaniesView? = null

    init {
        App.component.inject(this)
    }

    override fun injectView(view: CompaniesView) {
        this.companiesView = view
    }

    override fun loadCompanies() {
        companiesView?.showProgress()
        repo.getCompanies { companies ->
            companiesView?.hideProgress()
            companies?.symbolsList?.let {
                companiesView?.onCompaniesLoaded(it)
                return@getCompanies
            }
            companiesView?.onError()
        }
    }

    override fun onPause() {
        this.companiesView = null
    }
}
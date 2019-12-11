package com.example.financemodeling.views

import com.example.financemodeling.models.Company

interface CompaniesView: BaseView {

    fun initViews()
    fun onCompaniesLoaded(companies: List<Company>)
}
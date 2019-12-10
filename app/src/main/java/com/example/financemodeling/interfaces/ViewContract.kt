package com.example.financemodeling.interfaces

import com.example.financemodeling.api.models.History
import com.example.financemodeling.api.models.Company

interface ViewContract {

    fun onCompanies(companies: List<Company>)
    fun onHistory(histories: List<History>)
    fun onLoadError()
}
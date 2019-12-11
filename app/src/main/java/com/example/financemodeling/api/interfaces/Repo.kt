package com.example.financemodeling.api.interfaces

import com.example.financemodeling.models.Companies
import com.example.financemodeling.models.Histories

interface Repo {

    fun getHistories(symbol: String, fromDate: String, toDate: String, repo: (histories: Histories?) -> Unit)
    fun getCompanies(repo: (histories: Companies?) -> Unit)
}
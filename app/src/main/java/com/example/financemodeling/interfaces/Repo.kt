package com.example.financemodeling.interfaces

import com.example.financemodeling.api.models.Companies
import com.example.financemodeling.api.models.Histories
import java.util.*

interface Repo {

    fun getHistories(symbol: String, fromDate: Date, toDate: Date, repo: (histories: Histories?) -> Unit)
    fun getCompanies(repo: (histories: Companies?) -> Unit)
}
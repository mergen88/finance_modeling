package com.example.financemodeling.api

import com.example.financemodeling.api.interfaces.FinanceModelingApi
import com.example.financemodeling.api.interfaces.Repo
import com.example.financemodeling.models.Companies
import com.example.financemodeling.models.Histories
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RepoImpl: Repo {

    private val api: FinanceModelingApi

    init {
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://financialmodelingprep.com/api/v3/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        this.api = retrofit.create(FinanceModelingApi::class.java)
    }

    override fun getHistories(symbol: String, fromDate: String, toDate: String, repo: (histories: Histories?) -> Unit) {

        api.getCompanyHistory(symbol, fromDate, toDate).enqueue(object : Callback<Histories>{
            override fun onFailure(call: Call<Histories>, t: Throwable) {
                repo(null)
            }

            override fun onResponse(call: Call<Histories>, response: Response<Histories>) {
                repo(response.body())
            }
        })
    }

    override fun getCompanies(repo: (histories: Companies?) -> Unit) {
        api.getCompanyList().enqueue(object : Callback<Companies> {
            override fun onFailure(call: Call<Companies>, t: Throwable) {
                repo(null)
            }

            override fun onResponse(call: Call<Companies>, response: Response<Companies>) {
                repo(response.body())
            }

        })
    }

}
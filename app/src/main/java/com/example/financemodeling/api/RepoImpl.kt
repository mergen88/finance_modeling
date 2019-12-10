package com.example.financemodeling.api

import com.example.financemodeling.api.interfaces.FinanceModelingApi
import com.example.financemodeling.interfaces.Repo
import com.example.financemodeling.api.models.Companies
import com.example.financemodeling.api.models.Histories
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

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

    override fun getHistories(symbol: String, fromDate: Date, toDate: Date, repo: (histories: Histories?) -> Unit) {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val from = format.format(fromDate)
        val to = format.format(toDate)
        api.getCompanyHistory(symbol, from, to).enqueue(object : Callback<Histories>{
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
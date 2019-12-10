package com.example.financemodeling.api.interfaces

import com.example.financemodeling.api.models.Companies
import com.example.financemodeling.api.models.Histories
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FinanceModelingApi {

    @GET("company/stock/list")
    fun getCompanyList(): Call<Companies>

    @GET("historical-price-full/{name}")
    fun getCompanyHistory(
        @Path("name") name: String,
        @Query("from") dateFrom: String,
        @Query("to") dateTo: String): Call<Histories>
}
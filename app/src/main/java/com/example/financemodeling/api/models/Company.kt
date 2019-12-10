package com.example.financemodeling.api.models

import com.google.gson.annotations.SerializedName


data class Company (
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("name")
    val name:   String,
    @SerializedName("price")
    val price:  String
)
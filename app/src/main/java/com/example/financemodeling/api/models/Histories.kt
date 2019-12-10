package com.example.financemodeling.api.models

import com.google.gson.annotations.SerializedName

data class Histories (
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("historical")
    val historical: List<History>
)
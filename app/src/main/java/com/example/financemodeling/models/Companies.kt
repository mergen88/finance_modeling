package com.example.financemodeling.models

import com.google.gson.annotations.SerializedName

data class Companies (

    @SerializedName("symbolsList")
    val symbolsList: List<Company>?
)
package com.example.financemodeling.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class History (
    @SerializedName("date")
    val date: Date,
    @SerializedName("open")
    val open: Double,
    @SerializedName("high")
    val high: Double,
    @SerializedName("low")
    val low: Double,
    @SerializedName("close")
    val close: Double,
    @SerializedName("volume")
    val volume: Double,
    @SerializedName("unadjustedVolume")
    val unadjustedVolume: Double,
    @SerializedName("change")
    val change: Double,
    @SerializedName("changePercent")
    val changePercent: Double,
    @SerializedName("vwap")
    val vwap: Double,
    @SerializedName("label")
    val label: String,
    @SerializedName("changeOverTime")
    val changeOverTime: Double
)
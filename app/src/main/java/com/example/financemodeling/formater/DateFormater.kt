package com.example.financemodeling.formater

import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*

class DateFormater: ValueFormatter() {


    override fun getFormattedValue(value: Float): String {
        return SimpleDateFormat("dd MMM yy").format(Date(value.toLong()))
    }
}
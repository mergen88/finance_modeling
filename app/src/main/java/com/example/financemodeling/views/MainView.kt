package com.example.financemodeling.views

import androidx.fragment.app.Fragment


interface MainView: BaseView {


    fun loadFragment(fragment: Fragment)
}
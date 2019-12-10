package com.example.financemodeling.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.financemodeling.App
import com.example.financemodeling.interfaces.Presenter
import javax.inject.Inject

abstract class BaseActivity: AppCompatActivity() {

    @Inject lateinit var presenter: Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).component.inject(this)

    }

    override fun onResume() {
        super.onResume()
        lifecycle.addObserver(presenter)
    }
}
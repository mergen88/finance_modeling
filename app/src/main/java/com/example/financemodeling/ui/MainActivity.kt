package com.example.financemodeling.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.financemodeling.App
import com.example.financemodeling.R
import com.example.financemodeling.views.MainView
import com.example.financemodeling.presenter.MainPresenter
import com.example.financemodeling.ui.fragments.CompaniesFragment
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(),
    MainView {


    @Inject
    lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.component.inject(this)
        loadFragment(CompaniesFragment())
    }

    override fun onResume() {
        super.onResume()
        mainPresenter.injectView(this)
    }

    override fun onBackPressed() {
        hideProgress()
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }

    override fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_frame, fragment).addToBackStack(null).commit()
    }

    override fun showProgress() {
        progressBar?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar?.visibility = View.GONE
    }

    override fun onError() {
        Toast.makeText(applicationContext, R.string.load_error, Toast.LENGTH_LONG).show()
    }

    override fun onPause() {
        super.onPause()
        mainPresenter.onPause()
    }
}

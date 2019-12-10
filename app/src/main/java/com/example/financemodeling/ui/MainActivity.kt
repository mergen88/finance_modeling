package com.example.financemodeling.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.financemodeling.R
import com.example.financemodeling.interfaces.ViewContract
import com.example.financemodeling.api.models.History
import com.example.financemodeling.api.models.Company
import com.example.financemodeling.ui.fragments.CompaniesFragment
import com.example.financemodeling.ui.fragments.HistoriesFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : BaseActivity(), CompaniesFragment.OnFragmentInteractionListener, HistoriesFragment.OnFragmentInteractionListener,
    ViewContract {

    val companiesFragment = CompaniesFragment()
    var historiesFragment: HistoriesFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragments(companiesFragment)
    }

    override fun onResume() {
        super.onResume()
        presenter.injectView(this)
    }

    override fun onCompanies(companies: List<Company>) {
        companiesFragment.companiesLiveData.value = companies
        hideProgress()
    }

    override fun onHistory(histories: List<History>) {
        historiesFragment?.historiesLiveData?.value = histories
        hideProgress()
    }

    override fun onLoadError() {
        Toast.makeText(applicationContext, R.string.load_error, Toast.LENGTH_LONG).show()
        hideProgress()
    }

    override fun loadCompanies() {
        presenter.loadCompanies()
        showProgress()
    }

    override fun loadCompany(symbol: String) {
        historiesFragment = HistoriesFragment.newInstance(symbol)
        historiesFragment?.let {
            loadFragments(it)
        }
    }

    override fun loadHistory(symbol: String, from: Date, to: Date) {
        presenter.loadHistory(symbol, from, to)
        showProgress()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }

    private fun loadFragments(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_frame, fragment).addToBackStack(null).commit()
    }

    private fun showProgress() {
        progressBar?.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progressBar?.visibility = View.GONE
    }

}

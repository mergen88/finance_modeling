package com.example.financemodeling.ui.fragments

import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.financemodeling.App
import com.example.financemodeling.R
import com.example.financemodeling.adapter.CompanyAdapter
import com.example.financemodeling.models.Company
import com.example.financemodeling.presenter.CompaniesPresenter
import com.example.financemodeling.views.CompaniesView
import com.example.financemodeling.views.MainView
import kotlinx.android.synthetic.main.fragment_companies.*
import javax.inject.Inject


class CompaniesFragment : Fragment(), CompaniesView {


    @Inject lateinit var presenter: CompaniesPresenter
    private var companyAdapter: CompanyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)
        presenter.loadCompanies()
    }

    override fun onCompaniesLoaded(companies: List<Company>) {
        companyAdapter?.addItems(companies)
    }

    override fun onResume() {
        super.onResume()
        presenter.injectView(this)
    }

    override fun onError() {
        Toast.makeText(context, R.string.load_error, Toast.LENGTH_LONG).show()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_companies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun initViews() {
        context?.let {
            if (companyAdapter == null) {
                companyAdapter = CompanyAdapter(it, onItemClickListener)
            }
            recycleView.layoutManager = LinearLayoutManager(it)
            recycleView.adapter = companyAdapter
            recycleView.addItemDecoration(DividerItemDecoration(it, DividerItemDecoration.VERTICAL))
        }
        companySearchView?.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                companyAdapter?.filter?.filter(newText)
                return true
            }

        })
    }

    override fun hideProgress() {
        if (activity is MainView) {
            (activity as MainView).hideProgress()
        }
    }

    override fun showProgress() {
        if (activity is MainView) {
            (activity as MainView).showProgress()
        }
    }

    private val onItemClickListener = object : CompanyAdapter.OnItemClickListener{
        override fun onItemClick(item: Company) {
            if (activity is MainView) {
                (activity as MainView).loadFragment(HistoriesFragment.newInstance(item.symbol))
            }
        }
    }

}



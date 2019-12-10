package com.example.financemodeling.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.financemodeling.R
import com.example.financemodeling.adapter.CompanyAdapter
import com.example.financemodeling.api.models.Company
import kotlinx.android.synthetic.main.fragment_companies.*


class CompaniesFragment : Fragment() {

    private var companyAdapter: CompanyAdapter? = null
    private var listener: OnFragmentInteractionListener? = null
    val companiesLiveData = MutableLiveData<List<Company>?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listener?.loadCompanies()
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

    private fun initViews() {
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

    private val onItemClickListener = object : CompanyAdapter.OnItemClickListener{
        override fun onItemClick(item: Company) {
            listener?.loadCompany(item.symbol)
        }
    }

    override fun onResume() {
        super.onResume()
        companiesLiveData.observe(this, Observer {
            it?.let {
                    companies -> companyAdapter?.addItems(companies)
            }
        })
    }

    override fun onDetach() {
        super.onDetach()
        this.listener = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            this.listener = context
        }
    }

    interface OnFragmentInteractionListener {

        fun loadCompanies()
        fun loadCompany(symbol: String)
    }
}



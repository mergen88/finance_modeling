package com.example.financemodeling.adapter

import android.widget.Filter
import com.example.financemodeling.api.models.Company

class CompanyFilter(private val filterList: List<Company>, val adapter: CompanyAdapter): Filter() {

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        val filterResults = FilterResults()

        if (constraint.isNullOrEmpty()) {
            filterResults.count = filterList.size
            filterResults.values = filterList
        } else {
            val filteredCompanies = ArrayList<Company>()
            filterList.forEach {
                if (it.name.toUpperCase().contains(constraint.toString().toUpperCase())) {
                    filteredCompanies.add(it)
                }
            }
            filterResults.count = filteredCompanies.size
            filterResults.values = filteredCompanies
        }
        return filterResults
    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
        adapter.companies = results?.values as List<Company>
        adapter.notifyDataSetChanged()
    }

}
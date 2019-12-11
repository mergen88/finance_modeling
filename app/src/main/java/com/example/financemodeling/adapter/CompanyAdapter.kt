package com.example.financemodeling.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.financemodeling.R
import com.example.financemodeling.models.Company
import kotlinx.android.synthetic.main.company_item.view.*

class CompanyAdapter(private val context: Context, val itemClickListener: OnItemClickListener): RecyclerView.Adapter<ViewHolder>(), Filterable {

    var companies: List<Company> = ArrayList()
    private var filterList: List<Company> = ArrayList()
    private var filter: CompanyFilter? = null

    fun addItems(companies: List<Company>) {
        this.companies = companies
        this.filterList = companies
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.company_item, parent, false))
    }

    override fun getItemCount(): Int {
        return companies.size
    }

    override fun getFilter(): Filter? {
        if (filter == null) {
            filter = CompanyFilter(filterList, this)
        }
        return filter
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(companies[position], itemClickListener)
    }

    interface OnItemClickListener {
        fun onItemClick(item: Company)
    }
}
class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(company: Company, itemClickListener: CompanyAdapter.OnItemClickListener) {
        itemView.price.text = company.price
        itemView.company_name.text = company.name
        itemView.setOnClickListener{
            itemClickListener.onItemClick(company)
        }
    }
}
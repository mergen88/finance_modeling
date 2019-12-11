package com.example.financemodeling.ui.fragments


import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.core.view.forEach
import com.example.financemodeling.App
import com.example.financemodeling.R
import com.example.financemodeling.models.History
import com.example.financemodeling.formater.DateFormater
import com.example.financemodeling.presenter.HistoriesPresenter
import com.example.financemodeling.views.HistoriesView
import com.example.financemodeling.views.MainView
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.android.synthetic.main.fragment_histories.*
import java.util.*
import javax.inject.Inject

class HistoriesFragment : Fragment(), OnChartValueSelectedListener, HistoriesView {


    @Inject lateinit var presenter: HistoriesPresenter
    private var symbol: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)
        arguments?.let {
            symbol = it.getString(SYMBOL_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_histories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initGraph()
    }

    override fun initButtons() {
        buttons_group?.setOnCheckedChangeListener { radioGroup, i ->
            radioGroup.forEach {
                (it as? CompoundButton)?.setTextColor(Color.GRAY)
            }
            radioGroup.findViewById<CompoundButton>(i).let {
                it.setTextColor(Color.WHITE)
                loadData(radioGroup.indexOfChild(it))
            }

        }
        defFiveDay?.isChecked = true
    }

    override fun onResume() {
        super.onResume()
        presenter.injectView(this)
        initButtons()
    }

    override fun onError() {
        Toast.makeText(context, R.string.load_error, Toast.LENGTH_LONG).show()
    }

    override fun onHistories(historyList: List<History>) {
        chart_graph.clear()
        val entries = ArrayList<Entry>()
        historyList.forEach {
            entries.add(Entry(it.date.time.toFloat(), it.close.toFloat()))
        }
        val lineDataSet = LineDataSet(entries, "Close")
        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(lineDataSet)
        chart_graph.data = LineData(dataSets)
    }

    override fun loadData(pos: Int) {
        val fromCalendar = Calendar.getInstance()
        val toCalendar = Calendar.getInstance()
        when(pos) {
            0 -> fromCalendar.add(Calendar.DAY_OF_MONTH, -5)
            1 -> fromCalendar.add(Calendar.MONTH, -1)
            2 -> fromCalendar.add(Calendar.MONTH, -6)
            3 -> fromCalendar.add(Calendar.YEAR, -1)
            4 -> fromCalendar.add(Calendar.YEAR, -5)
        }
        symbol?.let {
            presenter.loadHistories(it, fromCalendar.time, toCalendar.time)
        }

    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun initGraph() {
        chart_graph.setBackgroundColor(Color.WHITE)
        chart_graph.description.isEnabled = false
        chart_graph.setTouchEnabled(true)
        chart_graph.setOnChartValueSelectedListener(this)
        chart_graph.setDrawGridBackground(false)
        chart_graph.isDragEnabled = false
        chart_graph.setScaleEnabled(false)
        chart_graph.setPinchZoom(true)
        val xAxis : XAxis = chart_graph.xAxis
        xAxis.mAxisMaximum = 60f
        xAxis.valueFormatter = DateFormater()
    }

    override fun onNothingSelected() {

    }

    override fun onValueSelected(entry: Entry?, h: Highlight?) {
        entry?.y?.let {
            graphPrice.text = it.toString()
        }
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

    companion object {
        const val SYMBOL_PARAM = "symbol_param"
        fun newInstance(symbol: String) =
            HistoriesFragment().apply {
                arguments = Bundle().apply {
                    putString(SYMBOL_PARAM, symbol)
                }
            }
    }
}

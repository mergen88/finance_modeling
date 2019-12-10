package com.example.financemodeling.ui.fragments


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.view.forEach
import androidx.lifecycle.MutableLiveData
import com.example.financemodeling.R
import com.example.financemodeling.api.models.History
import com.example.financemodeling.formater.DateFormater
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.android.synthetic.main.fragment_histories.*
import java.util.*

class HistoriesFragment : Fragment(), OnChartValueSelectedListener {

    private var symbol: String? = null
    private var listener: OnFragmentInteractionListener? = null
    val historiesLiveData = MutableLiveData<List<History>?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        initButtons()
        initGraph()
    }

    private fun initButtons() {
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
        historiesLiveData.observe(this, androidx.lifecycle.Observer {
            it?.let {
                loadGraphData(it)
            }
        })
    }

    private fun loadData(pos: Int) {
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
            listener?.loadHistory(it, fromCalendar.time, toCalendar.time)
        }

    }

    private fun initGraph() {
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

    private fun loadGraphData(histories: List<History>) {
        chart_graph.clear()
        val entries = ArrayList<Entry>()
        histories.forEach {
            entries.add(Entry(it.date.time.toFloat(), it.close.toFloat()))
        }
        val lineDataSet = LineDataSet(entries, "Close")
        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(lineDataSet)
        chart_graph.data = LineData(dataSets)
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

    override fun onNothingSelected() {

    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        e?.y?.let {
            graphPrice.text = it.toString()
        }
    }

    interface OnFragmentInteractionListener {
        fun loadHistory(symbol: String, from: Date, to: Date)
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

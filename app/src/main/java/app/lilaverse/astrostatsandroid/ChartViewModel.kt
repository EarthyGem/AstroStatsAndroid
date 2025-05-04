package app.lilaverse.astrostatsandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.lilaverse.astrostatsandroid.model.Chart
import app.lilaverse.astrostatsandroid.model.ChartDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChartViewModel(private val chartDao: ChartDao) : ViewModel() {
    private val _charts = MutableStateFlow<List<Chart>>(emptyList())
    val charts: StateFlow<List<Chart>> = _charts.asStateFlow()

    init {
        viewModelScope.launch {
            chartDao.getAllCharts().collect {
                _charts.value = it
            }
        }
    }

    fun addChart(chart: Chart) {
        viewModelScope.launch {
            chartDao.insertChart(chart)
        }
    }
}

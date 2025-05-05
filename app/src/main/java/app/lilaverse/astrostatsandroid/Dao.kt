package app.lilaverse.astrostatsandroid.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ChartDao {

    @Query("SELECT * FROM Chart ORDER BY date DESC")
    fun getAllCharts(): Flow<List<Chart>>

    @Query("SELECT * FROM Chart WHERE id = :id")
    suspend fun getChartById(id: Int): Chart?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChart(chart: Chart)

    @Delete
    suspend fun deleteChart(chart: Chart)

    @Query("DELETE FROM Chart")
    suspend fun deleteAllCharts()
}

package app.lilaverse.astrostatsandroid.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ChartDao {
    @Query("SELECT * FROM Chart ORDER BY date DESC")
    fun getAllCharts(): Flow<List<Chart>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChart(chart: Chart)
}

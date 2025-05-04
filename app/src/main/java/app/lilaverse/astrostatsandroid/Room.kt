package app.lilaverse.astrostatsandroid.model

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@Database(entities = [Chart::class], version = 1)
@TypeConverters(Converters::class)
abstract class ChartDatabase : RoomDatabase() {
    abstract fun chartDao(): ChartDao

    companion object {
        @Volatile
        private var INSTANCE: ChartDatabase? = null

        fun getDatabase(context: Context): ChartDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ChartDatabase::class.java,
                    "chart_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

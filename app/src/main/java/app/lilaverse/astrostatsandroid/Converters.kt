package app.lilaverse.astrostatsandroid.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import app.lilaverse.astrostatsandroid.HouseCusps


class Converters {

    @TypeConverter
    fun fromDate(date: Date): Long = date.time

    @TypeConverter
    fun toDate(millis: Long): Date = Date(millis)

    @TypeConverter
    fun fromStringList(list: List<String>): String = Gson().toJson(list)

    @TypeConverter
    fun fromHouseCusps(houseCusps: HouseCusps): String {
        return Gson().toJson(houseCusps)
    }

    @TypeConverter
    fun toHouseCusps(json: String): HouseCusps {
        val type = object : TypeToken<HouseCusps>() {}.type
        return Gson().fromJson(json, type)
    }


    @TypeConverter
    fun toStringList(json: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(json, type)
    }
}

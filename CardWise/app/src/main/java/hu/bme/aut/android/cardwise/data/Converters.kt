package hu.bme.aut.android.cardwise.data

import androidx.room.TypeConverter
import java.text.DateFormat
import java.util.Date
import java.util.Locale

class Converters {
    @TypeConverter
    fun fromTimestamp(value: String): Date {
        return DateFormat.getDateInstance(DateFormat.SHORT, Locale.US).parse(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): String {
        return DateFormat.getDateInstance(DateFormat.SHORT, Locale.US).format(date)
    }
}
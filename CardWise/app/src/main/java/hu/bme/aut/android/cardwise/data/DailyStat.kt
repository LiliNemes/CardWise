package hu.bme.aut.android.cardwise.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.util.Date

/**
 * Data class for the storage of daily statistics. The attributes are:
 *  deckId: The Id of the deck for which we are examining data.
 *  userId: The Id of the user practicing.
 *  date: Which day we are examining.
 *  totalCount: How many cards were practiced.
 *  successCount: How many were solved correctly.
 */
@Entity(tableName = "DailyStat", primaryKeys = ["deckId", "date"])
data class DailyStat(
    @ColumnInfo(name = "deckId") var deckId: Long,
    @ColumnInfo(name = "userId") var userId: Long,
    @ColumnInfo(name = "date") var date: Date,
    @ColumnInfo(name = "totalCount") var totalCount: Long = 0,
    @ColumnInfo(name = "successCount") var successCount: Long = 0) {
}


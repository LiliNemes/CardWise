package hu.bme.aut.android.cardwise.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.util.Date

@Entity(tableName = "DailyStat", primaryKeys = ["deckId", "date"])

data class DailyStat(
    @ColumnInfo(name = "deckId") var deckId: Long,
    @ColumnInfo(name = "userId") var userId: Long,
    @ColumnInfo(name = "date") var date: Date,
    @ColumnInfo(name = "totalCount") var totalCount: Long = 0,
    @ColumnInfo(name = "successCount") var successCount: Long = 0) {
}


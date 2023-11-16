package hu.bme.aut.android.cardwise.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Card")
data class Card(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "deckId") var deckId: Long,
    @ColumnInfo(name = "question") var question: String,
    @ColumnInfo(name = "answer") var answer: String,
    @ColumnInfo(name = "totalCount") var totalCount: Long = 0,
    @ColumnInfo(name = "successCount") var successCount: Long = 0) {
}
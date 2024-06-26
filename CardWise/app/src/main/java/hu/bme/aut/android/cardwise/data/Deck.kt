package hu.bme.aut.android.cardwise.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Deck")
data class Deck(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "userId") var userId: Long,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "description") var description: String) {}
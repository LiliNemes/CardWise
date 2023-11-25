package hu.bme.aut.android.cardwise.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "password") var password: String,
    @ColumnInfo(name = "lastDeckId") var lastDeckId: Long = -1) {
}
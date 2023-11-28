package hu.bme.aut.android.cardwise.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class for the users with the following attributes:
 *  id: primary key of the table.
 *  name: the username of the user.
 *  password: the password of the user.
 *  lastDeckId: the Id of the deck last played by the user, initially -1.
 */
@Entity(tableName = "User")
data class User(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "password") var password: String,
    @ColumnInfo(name = "lastDeckId") var lastDeckId: Long = -1) {
}
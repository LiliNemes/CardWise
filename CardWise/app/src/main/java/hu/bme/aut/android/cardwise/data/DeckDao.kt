package hu.bme.aut.android.cardwise.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DeckDao {
    @Query("SELECT * FROM Deck")
    fun getAll(): List<Deck>

    @Insert
    fun insert(deck: Deck): Long

    @Update
    fun update(deck: Deck)

    @Delete
    fun deleteItem(deck: Deck)
}
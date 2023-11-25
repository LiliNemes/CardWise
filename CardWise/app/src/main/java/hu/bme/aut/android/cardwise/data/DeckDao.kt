package hu.bme.aut.android.cardwise.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DeckDao {
    @Query("SELECT * FROM Deck WHERE userId=:userId")
    fun getAllForUser(userId: Long): List<Deck>

    @Insert
    fun insert(deck: Deck): Long

    @Update
    fun update(deck: Deck)

    @Delete
    fun deleteItem(deck: Deck)

    @Query("SELECT * FROM Deck WHERE id=:deckId")
    fun getById(deckId: Long): Deck
}
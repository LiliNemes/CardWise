package hu.bme.aut.android.cardwise.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CardDao {
    @Query("SELECT * FROM Card")
    fun getAll(): List<Card>

    @Query("SELECT * FROM Card WHERE deckId = :deckId")
    fun getForDeck(deckId: Long): List<Card>

    @Insert
    fun insert(card: Card): Long

    @Update
    fun update(card: Card)

    @Delete
    fun deleteItem(card: Card)
}
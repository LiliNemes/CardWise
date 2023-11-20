package hu.bme.aut.android.cardwise.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.util.Date

@Dao
interface DailyStatDao {
    @Query("SELECT * FROM DailyStat")
    fun getAll(): List<DailyStat>

    @Query("SELECT * FROM DailyStat WHERE deckId = :deckId")
    fun getForDeck(deckId: Long): List<DailyStat>

    @Query("SELECT * FROM DailyStat WHERE deckId = :deckId AND date = :date")
    fun get(deckId: Long, date: Date): DailyStat

    @Query("SELECT * FROM DailyStat WHERE date = :date")
    fun getForDate(date: Date): List<DailyStat>

    @Query("SELECT * FROM DailyStat WHERE strftime('%Y', date) = :year AND  ltrim(strftime('%m', date), '0') = :month")
    fun getForMonth(year: Int, month: Int): List<DailyStat>

    @Insert
    fun insert(dailyStat: DailyStat)

    @Update
    fun update(dailyStat: DailyStat)

    @Delete
    fun deleteItem(dailyStat: DailyStat)
}
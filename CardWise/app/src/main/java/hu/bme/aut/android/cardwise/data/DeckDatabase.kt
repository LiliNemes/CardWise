package hu.bme.aut.android.cardwise.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Deck::class], version = 1)
abstract class DeckDatabase:RoomDatabase() {
    abstract fun DeckDao(): DeckDao

    companion object {
        fun getDatabase(applicationContext: Context): DeckDatabase {
            return Room.databaseBuilder(
                applicationContext,
                DeckDatabase::class.java,
                "deck-list"
            ).build();
        }
    }
}
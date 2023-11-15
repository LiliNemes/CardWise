package hu.bme.aut.android.cardwise.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Deck::class], version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun DeckDao(): DeckDao

    companion object {
        fun getDatabase(applicationContext: Context): AppDatabase {
            return Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "deck-list"
            ).build();
        }
    }
}
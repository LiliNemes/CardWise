package hu.bme.aut.android.cardwise.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Deck::class, Card::class], version = 2)
abstract class AppDatabase:RoomDatabase() {
    abstract fun DeckDao(): DeckDao
    abstract fun CardDao(): CardDao

    companion object {
        fun getDatabase(applicationContext: Context): AppDatabase {
            return Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "deck-list"
            ).fallbackToDestructiveMigration().build();
        }
    }
}
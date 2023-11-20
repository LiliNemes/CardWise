package hu.bme.aut.android.cardwise.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Deck::class, Card::class, DailyStat::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase:RoomDatabase() {
    abstract fun DeckDao(): DeckDao
    abstract fun CardDao(): CardDao
    abstract fun DailyStatDao(): DailyStatDao

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
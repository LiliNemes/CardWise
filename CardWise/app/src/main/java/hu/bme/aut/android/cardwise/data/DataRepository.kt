package hu.bme.aut.android.cardwise.data

import android.content.Context
import kotlin.concurrent.thread

class DataRepository(applicationContext: Context) {

    private val database: AppDatabase = AppDatabase.getDatabase(applicationContext)

    fun createTestData() {
        thread {
            database.DeckDao().insert(Deck(null, "name", "desc"))
        }
    }

    fun getAllDecks(): List<Deck> {
        return database.DeckDao().getAll()
    }

    fun insertDeck(item :Deck): Long {
        return database.DeckDao().insert(item)
    }

    fun deleteDeck(item: Deck) {
        database.DeckDao().deleteItem(item)
    }
}
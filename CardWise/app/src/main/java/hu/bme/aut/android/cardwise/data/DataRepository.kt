package hu.bme.aut.android.cardwise.data

import android.content.Context
import kotlin.concurrent.thread

class DataRepository(applicationContext: Context) {

    private val database: AppDatabase = AppDatabase.getDatabase(applicationContext)

    fun createTestData() {
        thread {
            database.clearAllTables()
            val deckId1 = database.DeckDao().insert(Deck(null, "Deck1", "This is deck one"))
            database.CardDao().insert(Card(null, deckId1, "1+1", "2"))
            database.CardDao().insert(Card(null, deckId1,"First letter of alphabet","a"))
            database.CardDao().insert(Card(null, deckId1, "2+2", "4"))
            database.CardDao().insert(Card(null, deckId1, "3*2", "6"))
            database.CardDao().insert(Card(null, deckId1, "5-5", "0"))

            val deckId2 = database.DeckDao().insert(Deck(null, "Deck2", "This is deck two"))
            database.CardDao().insert(Card(null, deckId2, "Question1", "Answer1"))
            database.CardDao().insert(Card(null, deckId2,
                "This is a really long Question2 with a lot of stuff. Maybe multipe lines. Or even more."
                , "And the matching answer is also very long. It might take multiple lines. yes this is it. 1234567890. Piggies for the win."))

            val deckId3 = database.DeckDao().insert(Deck(null, "Deck3", "This is deck three"))
            database.CardDao().insert(Card(null, deckId3, "QuestionA", "AnswerA"))
            database.CardDao().insert(Card(null, deckId3, "QuestionB", "AnswerB"))
        }
    }

    fun getAllDecks(): List<Deck> {
        return database.DeckDao().getAll()
    }

    fun getLastDeckUsed(): Deck {
        return database.DeckDao().getAll().first()
    }

    fun insertDeck(item :Deck): Long {
        return database.DeckDao().insert(item)
    }

    fun deleteDeck(item: Deck) {
        database.DeckDao().deleteItem(item)
    }

    fun getCardsForDeck(deckId: Long): List<Card> {
        return database.CardDao().getForDeck(deckId)
    }

    fun getDeck(deckId: Long): Deck {
        return database.DeckDao().getById(deckId)
    }

    fun insertCard(item: Card): Long? {
        return database.CardDao().insert(item)
    }

    fun deleteCard(item: Card) {
        database.CardDao().deleteItem(item)
    }

    fun updateCard(item: Card) {
        database.CardDao().update(item)
    }

}
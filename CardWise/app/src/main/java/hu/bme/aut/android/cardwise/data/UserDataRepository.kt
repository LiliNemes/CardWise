package hu.bme.aut.android.cardwise.data

import android.content.Context
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import kotlin.concurrent.thread

class UserDataRepository(applicationContext: Context, private val userId: Long) {

    private val database: AppDatabase = AppDatabase.getDatabase(applicationContext)

    fun getAllDecks(): List<Deck> {
        return database.DeckDao().getAllForUser(userId)
    }

    fun setLastDeckUsed(item: Deck) {
        val user = database.UserDao().getById(userId)
        user.lastDeckId = item.id!!
        database.UserDao().update(user)
    }

    fun getLastDeckUsed(): Deck? {
        val user = database.UserDao().getById(userId)
        if (user.lastDeckId < 0) return null
        return database.DeckDao().getById(user.lastDeckId)
    }

    fun insertDeck(item :Deck): Long {
        item.userId = userId
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

    fun updateDailyStat(deckId: Long, time: Date, success: Boolean) {
        val ds = database.DailyStatDao().get(deckId, time)
        if (ds == null)
        {
            database.DailyStatDao().insert(DailyStat(deckId, time, 1, if (success) 1 else 0))
        }
        else
        {
            ds.totalCount++
            if (success) ds.successCount++
            database.DailyStatDao().update(ds)
        }
    }

    fun getDeckStat(deckId: Long) : DeckStat {
        val cards = database.CardDao().getForDeck(deckId)

        var neverCount :Int = 0
        var under20: Int = 0
        var under50above20: Int = 0
        var under80above50: Int = 0
        var above80: Int = 0

        for (card in cards) {
            if (card.totalCount == 0L) {
                neverCount++
            }
            else{
                val ratio = card.successCount.toDouble()  / card.totalCount.toDouble()
                when {
                    ratio < 0.2 -> under20++
                    ratio < 0.5 -> under50above20++
                    ratio < 0.8 -> under80above50++
                    else -> above80++
                }
            }
        }

        return DeckStat(deckId, neverCount, under20, under50above20, under80above50, above80)
    }

    fun getDailyUsage(): List<LocalDate> {
        return database.DailyStatDao().getAll()
            .map { it -> it.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() }
            .distinct();
    }

    fun getUserId(): Long {
        return userId
    }
}
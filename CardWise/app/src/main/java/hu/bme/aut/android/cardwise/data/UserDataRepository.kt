package hu.bme.aut.android.cardwise.data

import android.content.Context
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import kotlin.math.roundToInt

class UserDataRepository(applicationContext: Context, private val userId: Long) {

    private val database: AppDatabase = AppDatabase.getDatabase(applicationContext)

    /**
     * Returns the users decks.
     */
    fun getAllDecks(): List<Deck> {
        return database.DeckDao().getAllForUser(userId)
    }

    /**
     * Sets the last deck used by the user.
     */
    fun setLastDeckUsed(item: Deck) {
        val user = database.UserDao().getById(userId)
        user.lastDeckId = item.id!!
        database.UserDao().update(user)
    }

    /**
     * Returns the deck last used by the user. If the user hasn't used any decks, returns null.
     */
    fun getLastDeckUsed(): Deck? {
        val user = database.UserDao().getById(userId)
        if (user.lastDeckId < 0) return null
        return database.DeckDao().getById(user.lastDeckId)
    }

    /**
     * Adds new deck.
     */
    fun insertDeck(item :Deck): Long {
        item.userId = userId
        return database.DeckDao().insert(item)
    }

    /**
     * Deletes deck.
     */
    fun deleteDeck(item: Deck) {
        database.DeckDao().deleteItem(item)
    }

    /**
     * Returns the list of cards in a deck.
     */
    fun getCardsForDeck(deckId: Long): List<Card> {
        return database.CardDao().getForDeck(deckId)
    }

    /**
     * AReturns the deck with the given id.
     */
    fun getDeck(deckId: Long): Deck {
        return database.DeckDao().getById(deckId)
    }

    /**
     * Inserts a card to the database.
     */
    fun insertCard(item: Card): Long? {
        return database.CardDao().insert(item)
    }

    /**
     * Deletes a card.
     */
    fun deleteCard(item: Card) {
        database.CardDao().deleteItem(item)
    }

    /**
     * Updates a card.
     */
    fun updateCard(item: Card) {
        database.CardDao().update(item)
    }

    /**
     * Updates the daily statistics.
     */
    fun updateDailyStat(deckId: Long, time: Date, success: Boolean) {
        val ds = database.DailyStatDao().get(deckId, time)
        if (ds == null)
        {
            database.DailyStatDao().insert(DailyStat(deckId, userId, time, 1, if (success) 1 else 0))
        }
        else
        {
            ds.totalCount++
            if (success) ds.successCount++
            database.DailyStatDao().update(ds)
        }
    }

    /**
     * Gets how many cards of a deck fit into the certain categories given.
     */
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

    /**
     * Gets on which days the user used the application.
     */
    fun getDailyUsage(): List<LocalDate> {
        return database.DailyStatDao().getAllForUser(userId)
            .map { it.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() }
            .distinct();
    }

    fun getUserId(): Long {
        return userId
    }

    /**
     * Returns the list of all the users.
     */
    fun getUserTotalList(limit: Int): List<UserStatInfo> {
        var list = database.DailyStatDao().getAggregateUserStats()
        return list.mapIndexed { idx, it ->
                val user = database.UserDao().getById(it.userId)
                UserStatInfo(idx+1, user.name, it.total.toString())
        }.take(limit)
    }

    /**
     * Returns the ranked list of users.
     */
    fun getUserSuccessList(limit: Int): List<UserStatInfo> {
        var list = database.DailyStatDao().getAggregateUserStats()
        var sorted = list.sortedByDescending { it.success.toDouble()/it.total }.take(limit)

        return sorted.mapIndexed { idx, it ->
            val user = database.UserDao().getById(it.userId)
            val pct = it.success.toDouble()/it.total * 100
            UserStatInfo(idx+1, user.name, pct.roundToInt().toString() + " %")
        }
    }
}
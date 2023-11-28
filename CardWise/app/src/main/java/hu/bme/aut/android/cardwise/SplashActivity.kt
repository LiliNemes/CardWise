package hu.bme.aut.android.cardwise

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import hu.bme.aut.android.cardwise.data.AppDatabase
import hu.bme.aut.android.cardwise.data.Card
import hu.bme.aut.android.cardwise.data.DailyStat
import hu.bme.aut.android.cardwise.data.Deck
import hu.bme.aut.android.cardwise.data.User
import java.sql.Date
import java.time.LocalDate
import kotlin.concurrent.thread
import kotlin.random.Random

/**
    Activity responsible for calling the splash screen.
 */
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        initApplication()
    }

    /**
     * Loads the premade dataset if wanted, starts the login activity.
     */
    private fun initApplication() {
        thread {
            //prepareDatabase(AppDatabase.getDatabase(applicationContext))
            runOnUiThread {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }

    /**
     * Initializing data for the demo run.
     */
    private fun prepareDatabase(database: AppDatabase) {
        database.clearAllTables()
        val userHans = database.UserDao().insert(User(name = "Hans", password = "Hans"))
        val userOtto = database.UserDao().insert(User(name = "Otto", password = "Otto"))
        val userGigi = database.UserDao().insert(User(name = "Gigi", password = "Gigi"))
        val deckId1 = createDeckForUser(database, userHans, "Mathematics", mathQuestions)
        val deckId2 = createDeckForUser(database, userHans, "Alphabet", alphabetQuestions)
        val deckId3 = createDeckForUser(database, userOtto, "Mathematics", mathQuestions)
        val deckId4 = createDeckForUser(database, userGigi, "Alphabet", alphabetQuestions)

        createUsageDataForUserAndDeck(database, userHans, deckId1)
        createUsageDataForUserAndDeck(database, userHans, deckId2)
        createUsageDataForUserAndDeck(database, userOtto, deckId3)
        createUsageDataForUserAndDeck(database, userGigi, deckId4)
    }

    /**
        Create storage for a deck with the given name, with the given cards for the user whose id
        was passed as a parameter. Creates storage for cards as well.
        database: database in which the deck and card data has to be stored.
        userId: the user whose decks and cards need storing.
        deckName: the name of the new deck.
        question set: the list of questions in the new deck.

        return deckId: The id of the created deck, comes from the insertion of the deck into the database.
     */
    private fun createDeckForUser(database: AppDatabase, userId: Long, deckName: String, questionSet: List<QNA>): Long {

        val deckId = database.DeckDao().insert(Deck(null, userId,deckName, "This is $deckName"))
        for (question in questionSet) {
            database.CardDao().insert(Card(null, deckId, question.question, question.answer))
        }
        return deckId
    }

    /**
     *  The users activity (randomly) in the last 30 days.
     */
    private fun createUsageDataForUserAndDeck(database: AppDatabase, userId: Long, deckId: Long) {
        val start = LocalDate.now().minusDays(30)
        for (i in 1..30) {
            val runTestToday = Random.nextInt(0,10) < 7
            val localDate = start.plusDays(i.toLong())
            val date :Date = Date.valueOf(localDate.toString())
            if (runTestToday) createUsageDataForUserForADeckAndDay(database, userId, deckId, date)
        }
    }

    /**
     *  The user randomly practices a random amount of cards with random rate of success on a day,
     *  and refreshes the statistics of their daily activity.
     */
    private fun createUsageDataForUserForADeckAndDay(database: AppDatabase, userId: Long, deckId: Long, date: Date) {

        val total = Random.nextLong(10, 20)
        val cards = database.CardDao().getForDeck(deckId)
        var totalSuccess = 0L

        for (i in 1..total) {
            val currentCardIdx = Random.nextInt(0, cards.size)
            val success = Random.nextInt(0,10) < 6
            val currentCard = cards[currentCardIdx]
            currentCard.totalCount++
            if (success) {
                currentCard.successCount++
                totalSuccess++
            }
            database.CardDao().update(currentCard)
        }

        database.DailyStatDao().insert(DailyStat(deckId, userId, date, total, totalSuccess))
    }

    /**
     * Static list of cards.
     */
    private val mathQuestions = listOf(
        QNA("How much is 1+1?", "2"),
        QNA("How much is 2+2?", "4"),
        QNA("How much is 7+2?", "9"),
        QNA("How much is 3+2?", "5"),
        QNA("How much is 5-5?", "0"),
        QNA("How much is 2*3?", "6"))

    /**
     * Static list of cards.
     */
    private val alphabetQuestions = listOf(
        QNA("First letter of alphabet?", "a"),
        QNA("Second letter of english alphabet?", "b"),
        QNA("Last letter of english alphabet?", "z"),
        QNA("What follows x?", "y"))

    /**
     * New data class which has two string attributes, named question and answer.
     */
    data class QNA(val question: String, val answer: String)
}
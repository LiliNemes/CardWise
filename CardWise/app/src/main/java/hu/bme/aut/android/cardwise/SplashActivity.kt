package hu.bme.aut.android.cardwise

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import hu.bme.aut.android.cardwise.data.AppDatabase
import hu.bme.aut.android.cardwise.data.Card
import hu.bme.aut.android.cardwise.data.Deck
import hu.bme.aut.android.cardwise.data.User
import kotlin.concurrent.thread

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        initApplication()
    }

    private fun initApplication() {
        thread {
            //prepareDatabase(AppDatabase.getDatabase(applicationContext))
            runOnUiThread {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }

    private fun prepareDatabase(database: AppDatabase) {
        database.clearAllTables()
        val userHans = database.UserDao().insert(User(name = "Hans", password = "Hans"))
        val userOtto = database.UserDao().insert(User(name = "Otto", password = "Otto"))
        createTestDataForUser(database, userHans)
    }

    private fun createTestDataForUser(database: AppDatabase, userId: Long) {
        val deckId1 = database.DeckDao().insert(Deck(null, userId,"Deck1", "This is deck one"))
        database.CardDao().insert(Card(null, deckId1, "1+1", "2"))
        database.CardDao().insert(Card(null, deckId1,"First letter of alphabet","a"))
        database.CardDao().insert(Card(null, deckId1, "2+2", "4"))
        database.CardDao().insert(Card(null, deckId1, "3*2", "6"))
        database.CardDao().insert(Card(null, deckId1, "5-5", "0"))

        val deckId2 = database.DeckDao().insert(Deck(null, userId, "Deck2", "This is deck two"))
        database.CardDao().insert(Card(null, deckId2, "Question1", "Answer1"))
        database.CardDao().insert(
            Card(null, deckId2,
            "This is a really long Question2 with a lot of stuff. Maybe multipe lines. Or even more."
            , "And the matching answer is also very long. It might take multiple lines. yes this is it. 1234567890. Piggies for the win.")
        )

        val deckId3 = database.DeckDao().insert(Deck(null,userId, "Deck3", "This is deck three"))
        database.CardDao().insert(Card(null, deckId3, "QuestionA", "AnswerA"))
        database.CardDao().insert(Card(null, deckId3, "QuestionB", "AnswerB"))
    }
}
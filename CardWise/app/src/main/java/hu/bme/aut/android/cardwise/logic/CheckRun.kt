package hu.bme.aut.android.cardwise.logic
import hu.bme.aut.android.cardwise.data.Card
import hu.bme.aut.android.cardwise.data.DataRepository

class CheckRun(private val dataRepository: DataRepository, private val deckId: Long) {

    private lateinit var currentCard: Card
    var total = 0
    var success = 0

    fun getNextCard(): Card {
        currentCard = Card(1, 1, "First letter of the alphabet?", "a")
        return currentCard
    }

    fun getCurrentCard(): Card {
        return currentCard
    }

    fun getDeckName(): Any {
        return dataRepository.getDeck(deckId).name
    }

    fun userAnswered(userAnswer: String?): Boolean {
        total++
        val success = userAnswer != null
        if (success) this.success++
        return success
    }

}



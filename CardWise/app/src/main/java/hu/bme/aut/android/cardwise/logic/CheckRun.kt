package hu.bme.aut.android.cardwise.logic
import hu.bme.aut.android.cardwise.data.Card
import hu.bme.aut.android.cardwise.data.DataRepository
import hu.bme.aut.android.cardwise.data.Deck
import java.util.Calendar

class CheckRun {

    private val dataRepository: DataRepository
    private val deckId: Long
    private val cards: List<Card>
    private val deck: Deck

    constructor(dataRepository: DataRepository, deckId: Long) {
        this.dataRepository = dataRepository
        this.deckId = deckId
        this.cards = dataRepository.getCardsForDeck(deckId)
        this.deck = dataRepository.getDeck(deckId)
    }

    private lateinit var currentCard: Card
    var total = 0
    var success = 0

    fun getNextCard(): Card {
        val rnd = (1..cards.size).random()
        currentCard = cards[(rnd - 1)]
        return currentCard
    }

    fun getCurrentCard(): Card {
        return currentCard
    }

    fun getDeckName(): String {
        return deck.name
    }

    fun userAnswered(userAnswer: String?): Boolean {

        val success = userAnswer != null && userAnswer.equals(currentCard.answer, true)

        total++
        currentCard.totalCount++

        if (success) {
            this.success++
            currentCard.successCount++
        }

        dataRepository.updateCard(currentCard)

        val calendar = Calendar.getInstance()
        dataRepository.updateDailyStat(deckId, calendar.time, success)

        return success
    }

}



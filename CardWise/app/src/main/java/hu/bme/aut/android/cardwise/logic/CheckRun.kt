package hu.bme.aut.android.cardwise.logic
import hu.bme.aut.android.cardwise.data.Card
import hu.bme.aut.android.cardwise.data.DataRepository
import hu.bme.aut.android.cardwise.data.Deck
import kotlinx.coroutines.delay
import kotlin.concurrent.thread

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
        total++
        val success = userAnswer != null && userAnswer.equals(currentCard.answer, true)
        if (success) this.success++
        return success
    }

}



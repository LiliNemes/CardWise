package hu.bme.aut.android.cardwise.logic
import hu.bme.aut.android.cardwise.data.Card
import hu.bme.aut.android.cardwise.data.UserDataRepository
import hu.bme.aut.android.cardwise.data.Deck
import java.util.Calendar

class CheckRun {

    private val userDataRepository: UserDataRepository
    private val deckId: Long
    private val cards: List<Card>
    private val deck: Deck

    constructor(userDataRepository: UserDataRepository, deckId: Long) {
        this.userDataRepository = userDataRepository
        this.deckId = deckId
        this.cards = userDataRepository.getCardsForDeck(deckId)
        this.deck = userDataRepository.getDeck(deckId)
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

        userDataRepository.updateCard(currentCard)

        val calendar = Calendar.getInstance()
        userDataRepository.updateDailyStat(deckId, calendar.time, success)

        return success
    }

}



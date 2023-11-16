package hu.bme.aut.android.cardwise.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.cardwise.data.Deck
import hu.bme.aut.android.cardwise.databinding.DialogNewDeckBinding
import hu.bme.aut.android.cardwise.R
import hu.bme.aut.android.cardwise.data.Card
import hu.bme.aut.android.cardwise.databinding.DialogNewCardBinding

class EditCardDialogFragment(private val deckId: Long, private val listener: EditCardDialogListener, private val card: Card? = null) :  DialogFragment() {
    interface EditCardDialogListener {
        fun onCardCreated(newItem: Card)
        fun onCardEdited(editedItem: Card)
    }

    private lateinit var binding: DialogNewCardBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNewCardBinding.inflate(LayoutInflater.from(activity))

        if (card != null)
        {
            binding.etQuestion.text.append(card.question)
            binding.etAnswer.text.append(card.answer)
        }

        val title = if (card!=null) R.string.edit_card_title else R.string.new_card_title

        return AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setView(binding.root)
            .setPositiveButton(R.string.button_ok) { _, _ ->
                if (isValid()) {
                    if (card != null)
                    {
                        card.answer = binding.etAnswer.text.toString()
                        card.question = binding.etQuestion.text.toString()
                        listener.onCardEdited(card)
                    }
                    else
                    {
                        listener.onCardCreated(getNewCard())
                    }
                }
            }
            .setNegativeButton(R.string.button_cancel, null)
            .create()
    }

    companion object {
        const val TAG = "EditCardDialogFragment"
    }

    private fun isValid() = binding.etQuestion.text.isNotEmpty() && binding.etAnswer.text.isNotEmpty()

    private fun getNewCard() = Card(
        deckId = this.deckId,
        question = binding.etQuestion.text.toString(),
        answer = binding.etAnswer.text.toString())
}
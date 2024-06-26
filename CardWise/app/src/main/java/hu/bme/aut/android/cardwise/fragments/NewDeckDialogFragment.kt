package hu.bme.aut.android.cardwise.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.cardwise.data.Deck
import hu.bme.aut.android.cardwise.databinding.DialogNewDeckBinding
import hu.bme.aut.android.cardwise.R

class NewDeckDialogFragment(private val listener: NewDeckDialogListener) :  DialogFragment() {
    interface NewDeckDialogListener {
        fun onDeckCreated(newItem: Deck)
    }
    private lateinit var binding: DialogNewDeckBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNewDeckBinding.inflate(LayoutInflater.from(activity))

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_deck)
            .setView(binding.root)
            .setPositiveButton(R.string.button_ok) { _, _ ->
                if (isValid()) {
                    listener.onDeckCreated(getDeck())
                }
            }
            .setNegativeButton(R.string.button_cancel, null)
            .create()
    }

    companion object {
        const val TAG = "NewDeckDialogFragment"
    }

    private fun isValid() = binding.etName.text.isNotEmpty()

    private fun getDeck() = Deck(
        name = binding.etName.text.toString(),
        userId = -1,
        description = binding.etDescription.text.toString()
    )
}
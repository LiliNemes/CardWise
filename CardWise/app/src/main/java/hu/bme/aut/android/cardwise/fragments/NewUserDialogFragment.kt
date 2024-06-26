package hu.bme.aut.android.cardwise.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.cardwise.R
import hu.bme.aut.android.cardwise.data.User
import hu.bme.aut.android.cardwise.databinding.DialogNewUserBinding

class NewUserDialogFragment(private val listener: NewUserDialogListener) :  DialogFragment() {
    interface NewUserDialogListener {
        fun onUserCreated(newUser: User)
    }
    private lateinit var binding: DialogNewUserBinding

    /**
     * Sets the event listeners.
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNewUserBinding.inflate(LayoutInflater.from(activity))

        return AlertDialog.Builder(requireContext())
            .setTitle("Register new user")
            .setView(binding.root)
            .setPositiveButton(R.string.button_ok) { _, _ ->
                if (isValid()) {
                    listener.onUserCreated(getUser())
                }
            }
            .setNegativeButton(R.string.button_cancel, null)
            .create()
    }

    companion object {
        const val TAG = "NewUserDialogFragment"
    }

    /**
     * It is only valid if you give both a username and a password.
     */
    private fun isValid() = binding.etName.text.isNotEmpty() && binding.etPassword.text.isNotEmpty()

    /**
     * Sets the attributes of the user we want to create.
     */
    private fun getUser() = User(
        name = binding.etName.text.toString(),
        password = binding.etPassword.text.toString(),
        lastDeckId = -1
    )
}
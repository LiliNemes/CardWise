package hu.bme.aut.android.cardwise

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.android.cardwise.data.AppDatabase
import hu.bme.aut.android.cardwise.data.User
import hu.bme.aut.android.cardwise.databinding.ActivityLoginBinding
import hu.bme.aut.android.cardwise.fragments.NewDeckDialogFragment
import hu.bme.aut.android.cardwise.fragments.NewUserDialogFragment
import kotlin.concurrent.thread

class LoginActivity: AppCompatActivity(), NewUserDialogFragment.NewUserDialogListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var database: AppDatabase

    /**
     * Gets the database, event managers of the buttons login and register.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = AppDatabase.getDatabase(applicationContext)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            CheckLogin(binding.etUsername.text.toString(), binding.etPassword.text.toString())
        }

        binding.btnRegister.setOnClickListener {
            NewUserDialogFragment(this).show(
            this.supportFragmentManager,
            NewDeckDialogFragment.TAG)
        }
    }

    /**
     * newUserDialogFragment event listener implementation. If the user(name) already exists
     * it alerts the user and doesn't add it to the database again. If not, it adds
     * the new user to the database and instantly navigates to the main activity of
     * the new user (so it should be empty).
     */
    override fun onUserCreated(newUser: User) {

        thread {
            val currentUser = database.UserDao().getByName(newUser.name)
            if (currentUser != null) {
                runOnUiThread {
                    AlertDialog.Builder(this)
                        .setMessage("User already exists.")
                        .setPositiveButton("Ok", null)
                        .show()
                }
            }
            else {
                val userId = database.UserDao().insert(newUser)
                runOnUiThread {
                    navigateToUserMain(userId)
                }
            }
        }
    }

    /**
     * Checks if the user's login data is correct. Firstly, whether the user exists,
     * secondly, whether the given password matches the username's password. If both are
     * correct, we navigate to the user's main activity page.
     */
    private fun CheckLogin(userName: String, password: String) {
        thread {

            val user = database.UserDao().getByName(userName);

            if (user == null) {
                runOnUiThread {
                    AlertDialog.Builder(this)
                        .setMessage("No such user.")
                        .setPositiveButton("Ok", null)
                        .show()
                }
                return@thread
            }

            if (user.password != password) {
                runOnUiThread {
                    AlertDialog.Builder(this)
                        .setMessage("Invalid password.")
                        .setPositiveButton("Ok", null)
                        .show()
                }
                return@thread
            }

            runOnUiThread {
                navigateToUserMain(user.id!!)
            }
        }
    }

    /**
     * Calls the main activity, with the added information userId, as the main activity
     * should be different for each user.
     */
    private fun navigateToUserMain(userId: Long) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(MainActivity.USER_ID_TAG, userId)
        startActivity(intent)
    }
}
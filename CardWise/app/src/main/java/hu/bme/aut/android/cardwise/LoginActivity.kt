package hu.bme.aut.android.cardwise

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.android.cardwise.data.AppDatabase
import hu.bme.aut.android.cardwise.data.User
import hu.bme.aut.android.cardwise.data.UserDao
import hu.bme.aut.android.cardwise.databinding.ActivityLoginBinding
import hu.bme.aut.android.cardwise.fragments.NewDeckDialogFragment
import hu.bme.aut.android.cardwise.fragments.NewUserDialogFragment
import kotlin.concurrent.thread

class LoginActivity: AppCompatActivity(), NewUserDialogFragment.NewUserDialogListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var database: AppDatabase

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

        binding.etUsername.text.append("Hans")
        binding.etPassword.text.append("Hans")
    }

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
                var userId = database.UserDao().insert(newUser)
                runOnUiThread {
                    navigateToUserMain(userId)
                }
            }
        }
    }

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

    private fun navigateToUserMain(userId: Long) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(MainActivity.USER_ID_TAG, userId)
        startActivity(intent)
    }
}
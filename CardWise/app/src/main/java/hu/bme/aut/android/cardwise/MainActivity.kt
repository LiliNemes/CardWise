package hu.bme.aut.android.cardwise
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import hu.bme.aut.android.cardwise.adapter.DeckAdapter
import hu.bme.aut.android.cardwise.data.Deck
import hu.bme.aut.android.cardwise.data.DeckDatabase
import hu.bme.aut.android.cardwise.fragments.NewDeckDialogFragment
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity(), NewDeckDialogFragment.NewDeckDialogListener {

    private lateinit var database: DeckDatabase
    private lateinit var adapter: DeckAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = DeckDatabase.getDatabase(this)

        setContentView(R.layout.activity_mainmenu)
    }

    override fun onDeckCreated(newItem: Deck) {
        thread {
            val insertId = database.DeckDao().insert(newItem)
            newItem.id = insertId
            runOnUiThread {
                adapter.addItem(newItem)
            }
        }
    }
}

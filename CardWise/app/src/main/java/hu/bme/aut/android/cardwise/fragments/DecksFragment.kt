package hu.bme.aut.android.cardwise.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.cardwise.UserDataRepositoryProvider
import hu.bme.aut.android.cardwise.R
import hu.bme.aut.android.cardwise.StudyActivity
import hu.bme.aut.android.cardwise.adapter.DeckAdapter
import hu.bme.aut.android.cardwise.data.Deck
import hu.bme.aut.android.cardwise.data.UserDataRepository
import hu.bme.aut.android.cardwise.databinding.FragmentDecksBinding
import kotlin.concurrent.thread

class DecksFragment : Fragment(), DeckAdapter.DeckClickListener, NewDeckDialogFragment.NewDeckDialogListener {

    private lateinit var binding: FragmentDecksBinding

    private lateinit var userDataRepository: UserDataRepository
    private lateinit var adapter: DeckAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        userDataRepository = (context as? UserDataRepositoryProvider)?.getUserDataRepository()
            ?: throw RuntimeException("Activity must implement the DataRepositoryProvider interface!")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDecksBinding.inflate(inflater, container, false)
        return binding.root;
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            NewDeckDialogFragment(this).show(
                activity?.supportFragmentManager!!,
                NewDeckDialogFragment.TAG
            )
        }
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = DeckAdapter(this)
        binding.rvMain.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMain.adapter = adapter
        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {
            val items = userDataRepository.getAllDecks()
            activity?.runOnUiThread {
                adapter.setAllItem(items)
            }
        }
    }

    override fun onDeckStarted(item: Deck) {
        val intent = Intent(requireContext(), StudyActivity::class.java)
        intent.putExtra(StudyActivity.STUDY_USER_ID_TAG, userDataRepository.getUserId())
        intent.putExtra(StudyActivity.STUDY_DECK_ID_TAG, item.id!!)
        startActivity(intent)
    }

    override fun onDeckDeleted(item: Deck) {
        val builder = AlertDialog.Builder(requireContext())
        builder
            .setMessage("Do you really want to delete deck ${item.name} ?")
            .setTitle("Delete deck")
            .setPositiveButton("Yes") { _, _ ->
                thread {
                    userDataRepository.deleteDeck(item)
                    activity?.runOnUiThread {
                        adapter.deleteItem(item)
                        Log.d("DeckDeleted", "Deck with id ${item.id} deleted")
                    }
                }
            }
            .setNegativeButton("No", null)

        builder.create().show()
    }

    override fun onDeckEdited(item: Deck) {
        var bundle = Bundle()
        bundle.putLong(DECK_ID_TAG, item.id!!)
        findNavController().navigate(R.id.action_decksFragment_to_cardsFragment, bundle)
    }

    override fun onDeckCreated(newItem: Deck) {
        thread {
            val insertId = userDataRepository.insertDeck(newItem)
            newItem.id = insertId
            activity?.runOnUiThread {
                adapter.addItem(newItem)
            Log.d("DeckCreated", "Deck with id $insertId created")
            }
        }
    }

    companion object {
        const val DECK_ID_TAG = "deckId"
    }

}
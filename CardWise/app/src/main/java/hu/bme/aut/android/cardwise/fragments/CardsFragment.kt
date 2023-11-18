package hu.bme.aut.android.cardwise.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.cardwise.DataRepositoryProvider
import hu.bme.aut.android.cardwise.R
import hu.bme.aut.android.cardwise.adapter.CardAdapter
import hu.bme.aut.android.cardwise.adapter.DeckAdapter
import hu.bme.aut.android.cardwise.data.Card
import hu.bme.aut.android.cardwise.data.DataRepository
import hu.bme.aut.android.cardwise.data.Deck
import hu.bme.aut.android.cardwise.databinding.FragmentCardsBinding
import hu.bme.aut.android.cardwise.databinding.FragmentDecksBinding
import hu.bme.aut.android.cardwise.fragments.DecksFragment.Companion.DECK_ID_TAG
import kotlin.concurrent.thread

class CardsFragment : Fragment(), CardAdapter.CardClickListener,
    EditCardDialogFragment.EditCardDialogListener {

    private lateinit var dataRepository: DataRepository
    private lateinit var adapter: CardAdapter

    private lateinit var binding: FragmentCardsBinding

    private var deckId: Long? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataRepository = (context as? DataRepositoryProvider)?.getDataRepository()
            ?: throw RuntimeException("Activity must implement the DataRepositoryProvider interface!")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        deckId = arguments?.getLong(DECK_ID_TAG)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardsBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addCard.setOnClickListener {
            EditCardDialogFragment(deckId!!, this).show(
                activity?.supportFragmentManager!!,
                EditCardDialogFragment.TAG
            )
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = CardAdapter(this)
        binding.rvCards.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCards.adapter = adapter
        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {
            if (this.deckId != null) {
                val items = dataRepository.getCardsForDeck(this.deckId!!)
                val deck = dataRepository.getDeck(this.deckId!!)
                activity?.runOnUiThread {
                    binding.toolbarTitle.text = "Deck: ${deck.name}"
                    adapter.setAllItem(items)
                }
            }
        }
    }

    override fun onCardCreated(newItem: Card) {
        thread {
            val insertId = dataRepository.insertCard(newItem)
            newItem.id = insertId
            activity?.runOnUiThread {
                adapter.addItem(newItem)
                Log.d("CardCreated", "Card with id $insertId for deck ${this.deckId} is created")
            }
        }
    }

    override fun onCardEdited(editedItem: Card) {
        thread {
            dataRepository.updateCard(editedItem)
            activity?.runOnUiThread {
                adapter.updateItem(editedItem)
                Log.d("CardUpdated", "Card with id ${editedItem.id} for deck ${this.deckId} is updated")
            }
        }
    }

    override fun onCardDeleteClicked(item: Card) {
        val builder = AlertDialog.Builder(requireContext())
        builder
            .setMessage("Do you really want to delete this card ?")
            .setTitle("Delete card")
            .setPositiveButton("Yes") { _, _ ->
                thread {
                    dataRepository.deleteCard(item)
                    activity?.runOnUiThread {
                        adapter.deleteItem(item)
                        Log.d("CardDeleted", "Card with id ${item.id} deleted")
                    }
                }
            }
            .setNegativeButton("No", null)

        builder.create().show()
    }

    override fun onCardEditClicked(item: Card) {
        EditCardDialogFragment(deckId!!, this, item).show(
            activity?.supportFragmentManager!!,
            EditCardDialogFragment.TAG
        )
    }
}
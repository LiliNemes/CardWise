package hu.bme.aut.android.cardwise

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.cardwise.adapter.DeckAdapter
import hu.bme.aut.android.cardwise.data.Deck
import hu.bme.aut.android.cardwise.data.DeckDatabase
import hu.bme.aut.android.cardwise.databinding.FragmentDecksBinding
import hu.bme.aut.android.cardwise.fragments.NewDeckDialogFragment
import kotlin.concurrent.thread

class DecksFragment : Fragment(), DeckAdapter.DeckClickListener, NewDeckDialogFragment.NewDeckDialogListener {
    private lateinit var binding: FragmentDecksBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDecksBinding.inflate(inflater, container, false)
        return binding.root;
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            NewDeckDialogFragment().show(
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
            val items = database.DeckDao().getAll()
            activity?.runOnUiThread {
                adapter.update(items)
            }
        }
    }

    override fun onItemChanged(item: Deck) {
        thread {
            database.DeckDao().update(item)
            Log.d("DecksFragment", "Deck update was successful")
        }
    }

    override fun onDeckCreated(newItem: Deck) {
        thread {
            val insertId = database.DeckDao().insert(newItem)
            newItem.id = insertId
            activity?.runOnUiThread {
                adapter.addItem(newItem)
            }
        }
    }
}
package hu.bme.aut.android.cardwise.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.cardwise.DataRepositoryProvider
import hu.bme.aut.android.cardwise.DeckIdProvider
import hu.bme.aut.android.cardwise.R
import hu.bme.aut.android.cardwise.adapter.DeckAdapter
import hu.bme.aut.android.cardwise.adapter.StudyAdapter
import hu.bme.aut.android.cardwise.data.DataRepository
import hu.bme.aut.android.cardwise.databinding.FragmentDecksBinding
import hu.bme.aut.android.cardwise.databinding.FragmentStudyListBinding
import hu.bme.aut.android.cardwise.databinding.StudyListBinding
import kotlin.concurrent.thread

class StudyListFragment : Fragment() {

    private lateinit var binding: FragmentStudyListBinding

    private lateinit var dataRepository: DataRepository
    private lateinit var adapter: StudyAdapter
    private var deckId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        deckId = (context as? DeckIdProvider)?.getSelectedDeckId()
            ?: throw RuntimeException("Activity must implement the DeckIdProvider interface!")
        dataRepository = (context as? DataRepositoryProvider)?.getDataRepository()
            ?: throw RuntimeException("Activity must implement the DataRepositoryProvider interface!")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudyListBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = StudyAdapter()
        binding.rvCards.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCards.adapter = adapter
        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {
            val items = dataRepository.getCardsForDeck(this.deckId!!)
            activity?.runOnUiThread {
                adapter.setAllItem(items)
            }
        }
    }
}
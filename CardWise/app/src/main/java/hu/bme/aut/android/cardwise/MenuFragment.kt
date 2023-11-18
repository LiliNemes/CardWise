package hu.bme.aut.android.cardwise

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.cardwise.data.DataRepository
import hu.bme.aut.android.cardwise.databinding.FragmentMenuBinding
import kotlin.concurrent.thread

class MenuFragment : Fragment() {
    private lateinit var binding : FragmentMenuBinding
    private lateinit var dataRepository: DataRepository

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataRepository = (context as? DataRepositoryProvider)?.getDataRepository()
            ?: throw RuntimeException("Activity must implement the DataRepositoryProvider interface!")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnDecks.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_decksFragment2)
        }
        binding.btnstats.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_statsFragment2)
        }
        binding.btnCheckLast.setOnClickListener {
            thread {
                val intent = Intent(requireContext(), CheckActivity::class.java)
                val lastDeck = dataRepository.getLastDeckUsed()
                intent.putExtra(CheckActivity.CHECK_DECK_ID_TAG, lastDeck.id)
                activity?.runOnUiThread {
                    startActivity(intent)
                }
            }
        }

    }
}
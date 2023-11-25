package hu.bme.aut.android.cardwise.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.bme.aut.android.cardwise.UserDataRepositoryProvider
import hu.bme.aut.android.cardwise.data.UserDataRepository
import hu.bme.aut.android.cardwise.databinding.FragmentStatsDecksChartBinding

class StatDecksChartFragment : Fragment() {

    private lateinit var binding: FragmentStatsDecksChartBinding
    private lateinit var userDataRepository: UserDataRepository

    override fun onAttach(context: Context) {
        super.onAttach(context)
        userDataRepository = (context as? UserDataRepositoryProvider)?.getUserDataRepository()
            ?: throw RuntimeException("Activity must implement the DataRepositoryProvider interface!")
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStatsDecksChartBinding.inflate(inflater, container, false)
        return binding.root;
    }
}
package hu.bme.aut.android.cardwise.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.cardwise.UserDataRepositoryProvider
import hu.bme.aut.android.cardwise.adapter.UserAdapter
import hu.bme.aut.android.cardwise.data.UserDataRepository
import hu.bme.aut.android.cardwise.data.UserStatInfo
import hu.bme.aut.android.cardwise.databinding.FragmentStatsUserChartBinding
import kotlin.concurrent.thread

class StatUserChartFragment : Fragment() {

    private lateinit var binding: FragmentStatsUserChartBinding
    private lateinit var userDataRepository: UserDataRepository
    private lateinit var questionsAdapter: UserAdapter
    private lateinit var successAdapter: UserAdapter

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
        binding = FragmentStatsUserChartBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        questionsAdapter = UserAdapter()
        binding.rvQuestions.layoutManager = LinearLayoutManager(requireContext())
        binding.rvQuestions.adapter = questionsAdapter

        successAdapter = UserAdapter()
        binding.rvSuccess.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSuccess.adapter = successAdapter
        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {

            val questionItems = userDataRepository.getUserTotalList(5)
            val successItems = userDataRepository.getUserSuccessList(5)

            activity?.runOnUiThread {
                questionsAdapter.setAllItem(questionItems)
                successAdapter.setAllItem(successItems)
            }
        }
    }
}
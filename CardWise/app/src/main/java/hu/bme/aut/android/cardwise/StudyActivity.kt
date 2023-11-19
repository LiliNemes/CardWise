package hu.bme.aut.android.cardwise

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.android.cardwise.data.DataRepository
import hu.bme.aut.android.cardwise.databinding.ActivityStudyBinding
import hu.bme.aut.android.cardwise.fragments.DatePickerDialogFragment
import hu.bme.aut.android.cardwise.fragments.StudyFragmentPagerAdapter
import java.util.Date
import kotlin.concurrent.thread

class StudyActivity : AppCompatActivity(), DataRepositoryProvider, DeckIdProvider, DatePickerDialogFragment.DatePickerDialogListener {

    private lateinit var binding: ActivityStudyBinding
    private var deckId: Long? = null
    private lateinit var dataRepository: DataRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataRepository = DataRepository(this)

        binding = ActivityStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = StudyFragmentPagerAdapter(supportFragmentManager)
        binding.viewpager.adapter = adapter

        deckId = this.intent.getLongExtra(STUDY_DECK_ID_TAG, -1)

        binding.btnStartTest.setOnClickListener {
            val intent = Intent(this, CheckActivity::class.java)
            intent.putExtra(CheckActivity.CHECK_DECK_ID_TAG, deckId)
            startActivity(intent)
        }

        binding.btnSetDeadline.setOnClickListener {
            val datePickFragment = DatePickerDialogFragment(this)
            datePickFragment.show(supportFragmentManager, "datePicker")
        }

        setTitle()
    }

    private fun setTitle()
    {
        thread {
            val deck = dataRepository.getDeck(deckId!!)
            runOnUiThread {
                binding.toolbarTitle.text = "Deck: ${deck.name}"
            }
        }
    }

    override fun getDataRepository(): DataRepository {
        return dataRepository
    }

    override fun getSelectedDeckId(): Long {
        return deckId!!
    }

    override fun onDatePicked(date: Date) {
        TODO("Not yet implemented")
    }

    companion object {
        const val STUDY_DECK_ID_TAG = "StudyDeckId"
    }
}

interface DeckIdProvider {
    fun getSelectedDeckId() :Long
}


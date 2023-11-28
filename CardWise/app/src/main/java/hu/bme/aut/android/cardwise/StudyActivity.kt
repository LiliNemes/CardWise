package hu.bme.aut.android.cardwise

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.android.cardwise.data.UserDataRepository
import hu.bme.aut.android.cardwise.databinding.ActivityStudyBinding
import hu.bme.aut.android.cardwise.fragments.DatePickerDialogFragment
import hu.bme.aut.android.cardwise.fragments.StudyFragmentPagerAdapter
import java.util.Date
import kotlin.concurrent.thread

class StudyActivity : AppCompatActivity(), UserDataRepositoryProvider, DeckIdProvider, DatePickerDialogFragment.DatePickerDialogListener {

    private lateinit var binding: ActivityStudyBinding
    private var deckId: Long? = null
    private lateinit var userDataRepository: UserDataRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userId = this.intent.getLongExtra(STUDY_USER_ID_TAG, -1)
        deckId = this.intent.getLongExtra(STUDY_DECK_ID_TAG, -1)

        userDataRepository = UserDataRepository(this, userId)

        binding = ActivityStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = StudyFragmentPagerAdapter(supportFragmentManager)
        binding.viewpager.adapter = adapter

        binding.btnStartTest.setOnClickListener {
            val intent = Intent(this, CheckActivity::class.java)
            intent.putExtra(CheckActivity.USER_ID_TAG, userId)
            intent.putExtra(CheckActivity.CHECK_DECK_ID_TAG, deckId)
            startActivity(intent)
        }

        binding.btnSetDeadline.setOnClickListener {
            val datePickFragment = DatePickerDialogFragment()
            datePickFragment.show(supportFragmentManager, "datePicker")
        }

        setTitle()
    }

    private fun setTitle()
    {
        thread {
            val deck = userDataRepository.getDeck(deckId!!)
            userDataRepository.setLastDeckUsed(deck)
            runOnUiThread {
                binding.toolbarTitle.text = "Deck: ${deck.name}"
            }
        }
    }

    override fun getUserDataRepository(): UserDataRepository {
        return userDataRepository
    }

    override fun getSelectedDeckId(): Long {
        return deckId!!
    }

    override fun onDatePicked(date: Date) {

    }

    companion object {
        const val STUDY_DECK_ID_TAG = "StudyDeckId"
        const val STUDY_USER_ID_TAG = "StudyUserId"
    }
}

interface DeckIdProvider {
    fun getSelectedDeckId() :Long
}


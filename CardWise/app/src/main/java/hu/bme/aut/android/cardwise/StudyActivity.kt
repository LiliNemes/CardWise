package hu.bme.aut.android.cardwise

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.android.cardwise.databinding.ActivityStudyBinding
import hu.bme.aut.android.cardwise.fragments.StudyFragmentPagerAdapter

class StudyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = StudyFragmentPagerAdapter(supportFragmentManager)
        binding.viewpager.adapter = adapter

        val deckId = this.intent.getLongExtra(STUDY_DECK_ID_TAG, -1)
    }

    companion object {
        const val STUDY_DECK_ID_TAG = "StudyDeckId"
    }
}


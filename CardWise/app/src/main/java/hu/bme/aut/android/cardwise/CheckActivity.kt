package hu.bme.aut.android.cardwise

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.DialogInterface
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.android.cardwise.data.DataRepository
import hu.bme.aut.android.cardwise.databinding.ActivityCheckBinding
import hu.bme.aut.android.cardwise.logic.CheckRun
import kotlin.concurrent.thread


class CheckActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckBinding
    private lateinit var dataRepository: DataRepository
    private lateinit var checkRun: CheckRun
    private lateinit var frontAnimator: AnimatorSet
    private lateinit var backAnimator: AnimatorSet

    var isFront = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataRepository = DataRepository(this)

        binding = ActivityCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var scale = applicationContext.resources.displayMetrics.density

        binding.cardFront.cameraDistance = 8000 * scale
        binding.cardBack.cameraDistance = 8000 * scale

        frontAnimator = AnimatorInflater.loadAnimator(applicationContext, R.animator.front_animator) as AnimatorSet
        backAnimator = AnimatorInflater.loadAnimator(applicationContext, R.animator.back_animator) as AnimatorSet

        binding.btnAnswerEntered.setOnClickListener {
            showAnswer(binding.etAnswer.text.toString())
        }

        binding.btnAnswerSkipped.setOnClickListener {
            showAnswer(null)
        }

        binding.btnNext.setOnClickListener {
            askQuestion()
        }

        binding.btnExit.setOnClickListener {
            super.onBackPressed()
        }

        binding.etAnswer.setOnKeyListener ( View.OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                showAnswer(binding.etAnswer.text.toString())
                return@OnKeyListener true
            }
            return@OnKeyListener false
        })

        val deckId = this.intent.getLongExtra(CHECK_DECK_ID_TAG, -1)
        initializeCheckRun(deckId)
    }

    private fun initializeCheckRun(deckId :Long)
    {
        thread {
            checkRun = CheckRun(dataRepository, deckId)
            runOnUiThread {
                setTitle()
                askQuestion()
            }
        }
    }


    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setMessage("Are you sure you want to stop ?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                super.onBackPressed()
            }
            .setNegativeButton("No", null)
            .show()
    }


    private fun setTitle() {
        val deckName = this.checkRun.getDeckName()
        binding.toolbarTitle.text = "Deck: $deckName"
    }

    private fun askQuestion() {
        thread {
            var card = checkRun.getNextCard()
            runOnUiThread {
                binding.checkQuestion.text = card.question
                binding.etAnswer.text.clear()
                if (!isFront) flipCard()
            }
        }
    }

    private fun showAnswer(userAnswer: String?) {
        thread {
            var success = checkRun.userAnswered(userAnswer)
            runOnUiThread {
                binding.rightAnswer.text = checkRun.getCurrentCard().answer
                binding.imgFailure.visibility = if (success) View.GONE else View.VISIBLE
                binding.imgSuccess.visibility = if (success) View.VISIBLE else View.GONE
                binding.totalCount.text = checkRun.total.toString()
                binding.successCount.text = checkRun.success.toString()
                if (isFront) flipCard()
            }
        }
    }

    private fun flipCard() {
        if (isFront)
        {
            frontAnimator.setTarget(binding.cardFront)
            backAnimator.setTarget(binding.cardBack)
            frontAnimator.start()
            backAnimator.start()
            isFront = false
            binding.frontButtons.visibility = View.GONE
            binding.backButtons.visibility = View.VISIBLE
        }
        else
        {
            frontAnimator.setTarget(binding.cardBack)
            backAnimator.setTarget(binding.cardFront)
            frontAnimator.start()
            backAnimator.start()
            isFront = true
            binding.frontButtons.visibility = View.VISIBLE
            binding.backButtons.visibility = View.GONE
        }
    }

    companion object {
        const val CHECK_DECK_ID_TAG = "CheckDeckId"
    }
}


package lpiemam.com.apppokecards.fragment


import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_quizz.*
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.model.PokemonQuestions
import lpiemam.com.apppokecards.model.Question
import lpiemam.com.apppokecards.model.UserManager
import lpiemam.com.apppokecards.viewmodel.QuizzViewModel
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class QuizzFragment : BaseFragment() {

    var user = UserManager.user

    lateinit var quizzViewModel: QuizzViewModel

    private var mPokemonQuestions: PokemonQuestions? = null
    private var mCurrentQuestion: Question? = null

    private var buttonList = ArrayList<Button?>()

    private var nbQuestion = 0
    private var nbCorrectAnswer = 0

    var hasAnswer: Boolean = false
    var hasAnswerCorrectly: Boolean = false

    var userWonQuiz: Boolean = false
    lateinit var countDownTimer: CountDownTimer
    private var counter = 5

    private var remainingTime: Long = 0

    private lateinit var chrono: TextView

    companion object {

        fun newInstance(): QuizzFragment {
            return QuizzFragment()
        }
    }

    override fun onResume() {

        if (remainingTime != 0L) {
            chrono.text = "0"
            setUpCountDownTimer(0)
        }

        super.onResume()
    }

    override fun onDestroy() {
        countDownTimer.cancel()
        super.onDestroy()
    }

    override fun onStop() {
        countDownTimer.cancel()
        super.onStop()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quizz, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        quizzViewModel = ViewModelProviders.of(activity!!).get(QuizzViewModel::class.java)

        setDrawerEnabled(false)

        mPokemonQuestions = quizzViewModel.generateQuestions()


        buttonList.add(activity_game_answer1_btn)
        buttonList.add(activity_game_answer2_btn)
        buttonList.add(activity_game_answer3_btn)
        buttonList.add(activity_game_answer4_btn)

        // Use the tag property to 'name' the buttons
        activity_game_answer1_btn?.tag = 0
        activity_game_answer2_btn?.tag = 1
        activity_game_answer3_btn?.tag = 2
        activity_game_answer4_btn?.tag = 3

        activity_game_answer1_btn?.setOnClickListener {
            onClick(it)
        }
        activity_game_answer2_btn?.setOnClickListener {
            onClick(it)
        }
        activity_game_answer3_btn?.setOnClickListener {
            onClick(it)
        }
        activity_game_answer4_btn?.setOnClickListener {
            onClick(it)
        }

        mCurrentQuestion = mPokemonQuestions!!.question
        this.displayQuestion(mCurrentQuestion!!)

        chrono = chronoTextView

        setUpCountDownTimer(6)
    }

    fun onClick(v: View) {
        activity_game_answer1_btn?.isEnabled = false
        activity_game_answer2_btn?.isEnabled = false
        activity_game_answer3_btn?.isEnabled = false
        activity_game_answer4_btn?.isEnabled = false
        if (counter > -1) {

            countDownTimer.cancel()

            val responseIndex = v.tag as Int

            if (responseIndex == mCurrentQuestion!!.answerIndex) {
                // Good answer
                hasAnswerCorrectly = true
                hasAnswer = true
                nbCorrectAnswer += 1
                v.setBackgroundResource(R.drawable.correct_selected_quizz_answer)
            } else {
                // Wrong answer
                hasAnswer = true
                v.setBackgroundResource(R.drawable.wrong_selected_quizz_answer)
                buttonList[mCurrentQuestion?.answerIndex!!]?.setBackgroundResource(R.drawable.correct_unselected_quizz_answer)
            }

            nbQuestion++

            Handler().postDelayed({

                setUpQuestions()
                // If this is the last question, ends the game.
                // Else, display the next question.
            }, 1000) // LENGTH_SHORT is usually 2 second long
        }
    }

    private fun endGame() {
        mainActivityListener?.replaceWithQuizzEndedFragment(userWonQuiz)
    }

    private fun displayQuestion(q: Question) {
        activity_game_question_text?.text = q.question
        activity_game_answer1_btn?.isEnabled = true
        activity_game_answer2_btn?.isEnabled = true
        activity_game_answer3_btn?.isEnabled = true
        activity_game_answer4_btn?.isEnabled = true
        activity_game_answer1_btn?.text = q.choiceList!![0]
        activity_game_answer2_btn?.text = q.choiceList!![1]
        activity_game_answer3_btn?.text = q.choiceList!![2]
        activity_game_answer4_btn?.text = q.choiceList!![3]
    }

    private fun setUpQuestions() {
        activity_game_answer1_btn?.setBackgroundResource(R.drawable.unselected_quizz_answer)
        activity_game_answer2_btn?.setBackgroundResource(R.drawable.unselected_quizz_answer)
        activity_game_answer3_btn?.setBackgroundResource(R.drawable.unselected_quizz_answer)
        activity_game_answer4_btn?.setBackgroundResource(R.drawable.unselected_quizz_answer)

        if (hasAnswer) {
            hasAnswer = false
            counter = 5
        }

        if (nbQuestion < 3) {
            mCurrentQuestion = mPokemonQuestions!!.question
            this.displayQuestion(mCurrentQuestion!!)
            setUpCountDownTimer(6)

        } else {
            if (nbCorrectAnswer >= 2) {
                user!!.coins += 50
                userWonQuiz = true
            }
            user?.dateLastQuizzEnded = Calendar.getInstance()
            quizzViewModel.updateUserInDB(UserManager.user!!)
            updateUserInfos()
            endGame()
        }
    }


    private fun setUpCountDownTimer(remainingTime: Long) {
        var countDownTimerRemaining: Long = if (remainingTime == 0L) {
            6L
        } else {
            remainingTime * 1000
        }
        countDownTimer = object : CountDownTimer(countDownTimerRemaining, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                this@QuizzFragment.remainingTime = millisUntilFinished / 1000
                chrono.text = counter.toString()
                counter--
            }

            override fun onFinish() {

                activity_game_answer1_btn?.setBackgroundResource(R.drawable.wrong_unselected_quizz_answer)
                activity_game_answer2_btn?.setBackgroundResource(R.drawable.wrong_unselected_quizz_answer)
                activity_game_answer3_btn?.setBackgroundResource(R.drawable.wrong_unselected_quizz_answer)
                activity_game_answer4_btn?.setBackgroundResource(R.drawable.wrong_unselected_quizz_answer)
                buttonList[mCurrentQuestion!!.answerIndex]!!.setBackgroundResource(R.drawable.correct_unselected_quizz_answer)

                Handler().postDelayed({

                    setUpQuestions()
                    // If this is the last question, ends the game.
                    // Else, display the next question.
                }, 1000) // LENGTH_SHORT is usually 2 second long


                counter = 5

                nbQuestion++

            }
        }
        countDownTimer.start()
    }

    override fun onPause() {
        countDownTimer.cancel()
        super.onPause()
    }
}


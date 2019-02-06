package lpiemam.com.apppokecards.fragment


import android.annotation.SuppressLint
import android.content.Context
import android.os.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_quizz.*
import lpiemam.com.apppokecards.*

import lpiemam.com.apppokecards.model.Manager
import lpiemam.com.apppokecards.model.PokemonQuestions
import lpiemam.com.apppokecards.model.Question
import java.util.*
import android.os.CountDownTimer
import kotlinx.android.synthetic.main.nav_header_main.*


/**
 * A simple [Fragment] subclass.
 */
class QuizzFragment : androidx.fragment.app.Fragment(), View.OnClickListener {

    var replaceFragmentListener: ReplaceFragmentListener? = null
    private var mQuestionTextView: TextView? = null
    private var mAnswerButton1: Button? = null
    private var mAnswerButton2: Button? = null
    private var mAnswerButton3: Button? = null
    private var mAnswerButton4: Button? = null

    private var mPokemonQuestions: PokemonQuestions? = null
    private var mCurrentQuestion: Question? = null


    private var nbQuestion = 0
    private var nbCorrectAnswer = 0
    var hasAnswer : Boolean = false
    var hasAnswerCorrectly : Boolean = false
    var hasFinishedQuizzToday : Boolean = false
    private lateinit var dateLastQuizzEnded : Calendar

    var chronoIsStarted = false
    private var mEnableTouchEvents: Boolean = false
    private lateinit var countDownTimer : CountDownTimer
    private var counter = 7
    private lateinit var chrono : TextView

    companion object {

        fun newInstance(): QuizzFragment {
            return QuizzFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        replaceFragmentListener = context as? ReplaceFragmentListener
        if (replaceFragmentListener == null) {
            throw ClassCastException("$context must implement OnCardSelectedListener")
        }
    }

    override fun onDetach() {

        replaceFragmentListener = null
        super.onDetach()
    }

    override fun onResume() {

        replaceFragmentListener!!.setUpBackButton(false)
        replaceFragmentListener!!.setDrawerEnabled(true)

        super.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quizz, container, false)

    }

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPokemonQuestions = Manager.generateQuestions()

        mEnableTouchEvents = true

        mQuestionTextView = activity_game_question_text
        mAnswerButton1 = activity_game_answer1_btn
        mAnswerButton2 = activity_game_answer2_btn
        mAnswerButton3 = activity_game_answer3_btn
        mAnswerButton4 = activity_game_answer4_btn

        // Use the tag property to 'name' the buttons
        mAnswerButton1!!.tag = 0
        mAnswerButton2!!.tag = 1
        mAnswerButton3!!.tag = 2
        mAnswerButton4!!.tag = 3

        mAnswerButton1!!.setOnClickListener(this)
        mAnswerButton2!!.setOnClickListener(this)
        mAnswerButton3!!.setOnClickListener(this)
        mAnswerButton4!!.setOnClickListener(this)

        mCurrentQuestion = mPokemonQuestions!!.question
        this.displayQuestion(mCurrentQuestion!!)

        chrono = chronoTextView

        object : CountDownTimer(7000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                chrono.text = counter.toString()
                counter--
            }

            override fun onFinish() {
                counter = 7

                if (hasAnswer) {
                    hasAnswer = false
                } else {
                    nbQuestion++
                }

                setTimerCountDown()
            }
        }.start()

    }

    override fun onClick(v: View) {
        val responseIndex = v.tag as Int

        if (responseIndex == mCurrentQuestion!!.answerIndex) {
            // Good answer
            val snackbar = Snackbar.make(v, "Correct", Snackbar.LENGTH_SHORT)
            snackbar.show()
            hasAnswerCorrectly = true
            hasAnswer = true
            nbCorrectAnswer += 1
        } else {
            // Wrong answer
            val snackbar = Snackbar.make(v, "Faux", Snackbar.LENGTH_SHORT)
            snackbar.show()
            hasAnswer = true
        }

        nbQuestion += 1
        mEnableTouchEvents = false

        Handler().postDelayed({
            mEnableTouchEvents = true
            // If this is the last question, ends the game.
            // Else, display the next question.
        }, 1000) // LENGTH_SHORT is usually 2 second long
    }

 /*  fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev)
    }*/

    private fun endGame() {
        replaceFragmentListener!!.replaceWithQuizzEndedFragment()
    }

    private fun displayQuestion(q: Question) {
        mQuestionTextView!!.text = q.question
        mAnswerButton1!!.text = q.choiceList!![0]
        mAnswerButton2!!.text = q.choiceList!![1]
        mAnswerButton3!!.text = q.choiceList!![2]
        mAnswerButton4!!.text = q.choiceList!![3]
    }

    private fun setTimerCountDown() {
        if (nbQuestion < 3) {
            mCurrentQuestion = mPokemonQuestions!!.question
            this.displayQuestion(mCurrentQuestion!!)

            object : CountDownTimer(7000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    chrono.text = counter.toString()
                    counter--
                }

                override fun onFinish() {
                    counter = 7

                    if (hasAnswer) {
                        hasAnswer = false
                    } else {
                        nbQuestion++
                    }
                    setTimerCountDown()
                }
            }.start()
        } else {
            if (nbCorrectAnswer >= 2) {
                Manager.userSiam.coins += 50
            }
            hasFinishedQuizzToday = true
            dateLastQuizzEnded = Calendar.getInstance()
            Manager.userSiam.dateLastQuizzEnded = dateLastQuizzEnded
            endGame()
        }
    }
}


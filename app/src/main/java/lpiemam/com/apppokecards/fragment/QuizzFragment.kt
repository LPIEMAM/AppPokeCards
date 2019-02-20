package lpiemam.com.apppokecards.fragment


import android.annotation.SuppressLint
import android.content.Context
import android.os.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_quizz.*
import lpiemam.com.apppokecards.*

import lpiemam.com.apppokecards.model.Manager
import lpiemam.com.apppokecards.model.PokemonQuestions
import lpiemam.com.apppokecards.model.Question
import java.util.*
import android.os.CountDownTimer
import kotlin.collections.ArrayList


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

    lateinit var countDownTimer: CountDownTimer
    private var mEnableTouchEvents: Boolean = false
    private var counter = 5
    private lateinit var chrono : TextView

    private var buttonList = ArrayList<Button?>()

    private var remainingTime : Long = 0

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

        replaceFragmentListener!!.setDrawerEnabled(true)
        replaceFragmentListener = null
        super.onDetach()
    }

    override fun onResume() {



        if(remainingTime != 0L) {
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

        replaceFragmentListener!!.setDrawerEnabled(false)

        mPokemonQuestions = Manager.generateQuestions()

        mEnableTouchEvents = true

        mQuestionTextView = activity_game_question_text
        mAnswerButton1 = activity_game_answer1_btn
        buttonList.add(mAnswerButton1)
        mAnswerButton2 = activity_game_answer2_btn
        buttonList.add(mAnswerButton2)
        mAnswerButton3 = activity_game_answer3_btn
        buttonList.add(mAnswerButton3)
        mAnswerButton4 = activity_game_answer4_btn
        buttonList.add(mAnswerButton4)

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

        setUpCountDownTimer(6)




    }

    override fun onClick(v: View) {
        countDownTimer.cancel()
        if(counter > 0) {


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
                buttonList[mCurrentQuestion!!.answerIndex]!!.setBackgroundResource(R.drawable.correct_unselected_quizz_answer)
            }


            /*if (hasAnswer) {
            countDownTimer.cancel()
            mCurrentQuestion = mPokemonQuestions!!.question
            this.displayQuestion(mCurrentQuestion!!)

            setUpQuestions()
        }*/


            nbQuestion++
            mEnableTouchEvents = false

            Handler().postDelayed({
                mEnableTouchEvents = true


                setUpQuestions()
                // If this is the last question, ends the game.
                // Else, display the next question.
            }, 1000) // LENGTH_SHORT is usually 2 second long
        }
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

    private fun setUpQuestions() {
        mAnswerButton1!!.setBackgroundResource(R.drawable.unselected_quizz_answer)
        mAnswerButton2!!.setBackgroundResource(R.drawable.unselected_quizz_answer)
        mAnswerButton3!!.setBackgroundResource(R.drawable.unselected_quizz_answer)
        mAnswerButton4!!.setBackgroundResource(R.drawable.unselected_quizz_answer)

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
                Manager.userSiam.coins += 50
            }
            hasFinishedQuizzToday = true
            dateLastQuizzEnded = Calendar.getInstance()
            Manager.userSiam.dateLastQuizzEnded = dateLastQuizzEnded
            endGame()
        }
    }



    private fun setUpCountDownTimer(remainingTime : Long) {
        var countDownTimerRemaining : Long
        if(remainingTime == 0L) {
            countDownTimerRemaining = 6L
        } else {
            countDownTimerRemaining = remainingTime * 1000
        }
        countDownTimer = object : CountDownTimer(countDownTimerRemaining, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                this@QuizzFragment.remainingTime = millisUntilFinished/1000
                chrono.text = counter.toString()
                counter--
            }

            override fun onFinish() {

                mAnswerButton1!!.setBackgroundResource(R.drawable.wrong_unselected_quizz_answer)
                mAnswerButton2!!.setBackgroundResource(R.drawable.wrong_unselected_quizz_answer)
                mAnswerButton3!!.setBackgroundResource(R.drawable.wrong_unselected_quizz_answer)
                mAnswerButton4!!.setBackgroundResource(R.drawable.wrong_unselected_quizz_answer)
                buttonList[mCurrentQuestion!!.answerIndex]!!.setBackgroundResource(R.drawable.correct_unselected_quizz_answer)

                Handler().postDelayed({
                    mEnableTouchEvents = true


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


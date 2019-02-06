package lpiemam.com.apppokecards.fragment


import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_collection.*
import kotlinx.android.synthetic.main.fragment_quizz.*
import kotlinx.android.synthetic.main.fragment_shop.*
import kotlinx.android.synthetic.main.fragment_shop.view.*
import kotlinx.android.synthetic.main.pack_opening_dialog_layout.*
import lpiemam.com.apppokecards.*

import lpiemam.com.apppokecards.adapter.ShopAdapter
import lpiemam.com.apppokecards.adapter.UserCardsAdapter
import lpiemam.com.apppokecards.model.CardsPack
import lpiemam.com.apppokecards.model.Manager
import lpiemam.com.apppokecards.model.PokemonQuestions
import lpiemam.com.apppokecards.model.Question
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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

    private var mScore: Int = 0
    private var mNumberOfQuestions: Int = 0
    var hasAnswerCorrectly : Boolean = false
    var hasAnswerCorrectlyToday : Boolean = false
    private lateinit var dateLastCorrectAnswer : Calendar

    val BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE"
    val BUNDLE_STATE_SCORE = "currentScore"
    val BUNDLE_STATE_QUESTION = "currentQuestion"

    private var mEnableTouchEvents: Boolean = false

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPokemonQuestions = Manager.generateQuestions()



        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE)
            mNumberOfQuestions = savedInstanceState.getInt(BUNDLE_STATE_QUESTION)
        } else {
            mScore = 0
            mNumberOfQuestions = 4
        }

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

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(BUNDLE_STATE_SCORE, mScore)
        outState.putInt(BUNDLE_STATE_QUESTION, mNumberOfQuestions)

        super.onSaveInstanceState(outState)
    }

    override fun onClick(v: View) {
        val responseIndex = v.tag as Int

        if (responseIndex == mCurrentQuestion!!.answerIndex) {
            // Good answer
            val snackbar = Snackbar.make(v, "Correct", Snackbar.LENGTH_LONG)
            snackbar.show()
            Manager.userSiam.coins += 50
            hasAnswerCorrectly = true
            dateLastCorrectAnswer = Calendar.getInstance()
        } else {
            // Wrong answer
            val snackbar = Snackbar.make(v, "Faux", Snackbar.LENGTH_LONG)
            snackbar.show()
        }

        mEnableTouchEvents = false

        Handler().postDelayed({
            mEnableTouchEvents = true
            // If this is the last question, ends the game.
            // Else, display the next question.
            if (hasAnswerCorrectly) {
                hasAnswerCorrectlyToday = true
                Manager.userSiam.dateLastCorrectAnswwer = dateLastCorrectAnswer
                endGame()
            } else {
                mCurrentQuestion = mPokemonQuestions!!.question
                displayQuestion(mCurrentQuestion!!)
            }
        }, 2000) // LENGTH_SHORT is usually 2 second long
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
}


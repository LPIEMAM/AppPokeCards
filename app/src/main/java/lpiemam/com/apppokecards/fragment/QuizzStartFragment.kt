package lpiemam.com.apppokecards.fragment


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_quizz_start.*

import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.MainActivityListener
import lpiemam.com.apppokecards.model.User
import lpiemam.com.apppokecards.model.UserManager
import java.util.*


/**
 * A simple [Fragment] subclass.
 *
 */
class QuizzStartFragment : BaseFragment() {

    var user = UserManager.user

    companion object {
        fun newInstance(): QuizzStartFragment {
            return QuizzStartFragment()
        }
    }

    override fun onResume() {

        setUpBackButton(false)
        setDrawerEnabled(true)

        super.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setFragmentTitle("Quiz")

        return inflater.inflate(R.layout.fragment_quizz_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var dateOfDay = Calendar.getInstance()
        if (user?.dateLastQuizzEnded == null || (dateOfDay.timeInMillis - user?.dateLastQuizzEnded!!.timeInMillis >= 86400000)) {
            quizzEndedGroup.visibility = View.GONE
            quizzStartGroup.visibility = View.VISIBLE
        } else {
            quizzEndedGroup.visibility = View.VISIBLE
            quizzStartGroup.visibility = View.GONE
        }

        startButton.setOnClickListener {
            mainActivityListener?.replaceWithQuizzFragment()
        }
    }
}

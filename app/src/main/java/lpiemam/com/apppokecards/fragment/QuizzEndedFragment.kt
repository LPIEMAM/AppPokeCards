package lpiemam.com.apppokecards.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_quizz_ended.*
import lpiemam.com.apppokecards.R

/**
 * A simple [Fragment] subclass.
 *
 */
class QuizzEndedFragment : BaseFragment() {

    var userWonQuizz: Boolean = false

    companion object {

        fun newInstance(userWonQuizz: Boolean): QuizzEndedFragment {
            val quizzEndedFragment = QuizzEndedFragment()
            var args = Bundle()
            args.putBoolean("userWonQuiz", userWonQuizz)
            quizzEndedFragment.arguments = args
            return quizzEndedFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userWonQuizz = it.getBoolean("userWonQuiz")
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
        return inflater.inflate(R.layout.fragment_quizz_ended, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (userWonQuizz) {
            quizzPassedGroup.visibility = View.VISIBLE
            quizzFailedGroup.visibility = View.GONE
        } else {
            quizzPassedGroup.visibility = View.GONE
            quizzFailedGroup.visibility = View.VISIBLE
        }
    }
}

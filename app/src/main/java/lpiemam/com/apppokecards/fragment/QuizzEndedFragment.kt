package lpiemam.com.apppokecards.fragment


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.ReplaceFragmentListener
import lpiemam.com.apppokecards.model.Manager

/**
 * A simple [Fragment] subclass.
 *
 */
class QuizzEndedFragment : Fragment() {

    var replaceFragmentListener: ReplaceFragmentListener? = null
    var quizzFragment = Manager.quizzFragment

    companion object {

        fun newInstance(): QuizzEndedFragment {
            return QuizzEndedFragment()
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quizz_ended, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        System.out.println(quizzFragment.hasAnswerCorrectlyToday)
    }

}

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


/**
 * A simple [Fragment] subclass.
 *
 */
class QuizzStartFragment : Fragment() {

    var mainActivityListener: MainActivityListener? = null

    companion object {

        fun newInstance(): QuizzStartFragment {
            return QuizzStartFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivityListener = context as? MainActivityListener
        if (mainActivityListener == null) {
            throw ClassCastException("$context must implement OnCardSelectedListener")
        }
    }

    override fun onDetach() {

        mainActivityListener = null
        super.onDetach()
    }

    override fun onResume() {

        mainActivityListener!!.setUpBackButton(false)
        mainActivityListener!!.setDrawerEnabled(true)

        super.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mainActivityListener!!.setFragmentTitle("Quizz")
        return inflater.inflate(R.layout.fragment_quizz_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startButton.setOnClickListener {
            mainActivityListener!!.replaceWithQuizzFragment()
        }
    }

}

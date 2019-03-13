package lpiemam.com.apppokecards.fragment


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.MainActivityListener

/**
 * A simple [Fragment] subclass.
 *
 */
class QuizzEndedFragment : Fragment() {

    var mainActivityListener: MainActivityListener? = null

    companion object {

        fun newInstance(): QuizzEndedFragment {
            return QuizzEndedFragment()
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
        return inflater.inflate(R.layout.fragment_quizz_ended, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}

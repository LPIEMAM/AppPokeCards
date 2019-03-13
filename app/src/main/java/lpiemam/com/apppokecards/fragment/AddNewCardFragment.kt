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
class AddNewCardFragment : Fragment() {

    var mainActivityListener: MainActivityListener? = null


    companion object {

        fun newInstance(): AddNewCardFragment {
            return AddNewCardFragment()
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

        mainActivityListener!!.setUpBackButton(false)
        mainActivityListener!!.setDrawerEnabled(true)

        mainActivityListener = null
        super.onDetach()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_add_new_card, container, false)

        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mainActivityListener!!.setDrawerEnabled(false)
        mainActivityListener!!.setUpBackButton(true)


        super.onViewCreated(view, savedInstanceState)
    }



}

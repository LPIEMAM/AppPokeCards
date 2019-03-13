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
class AddNewCardFragment : BaseFragment() {

    companion object {

        fun newInstance(): AddNewCardFragment {
            return AddNewCardFragment()
        }
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

        setDrawerEnabled(false)
        setUpBackButton(true)

        super.onViewCreated(view, savedInstanceState)
    }

}

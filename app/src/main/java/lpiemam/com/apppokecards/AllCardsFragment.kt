package lpiemam.com.apppokecards


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class AllCardsFragment : Fragment() {

    var replaceFragmentListener: ReplaceFragmentListener? = null

    companion object {

        fun newInstance(): AllCardsFragment {
            return AllCardsFragment()
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_cards, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        replaceFragmentListener!!.setDrawerEnabled(true)
        replaceFragmentListener!!.setUpBackButton(false)
        super.onViewCreated(view, savedInstanceState)
    }
}

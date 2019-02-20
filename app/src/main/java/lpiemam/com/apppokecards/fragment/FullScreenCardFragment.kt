package lpiemam.com.apppokecards.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_full_screen_card.view.*
import lpiemam.com.apppokecards.MainActivity
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.ReplaceFragmentListener
import lpiemam.com.apppokecards.model.Card




class FullScreenCardFragment : Fragment() {
    private var card : Card? = null
    private var wasPreviousScreenUserDetail : Boolean? = null
    private var listener: ReplaceFragmentListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            card = arguments?.getParcelable("card")
            wasPreviousScreenUserDetail = arguments?.getBoolean("wasPreviousScreenUserDetail")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_full_screen_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (context as MainActivity).supportActionBar!!.hide()
        Picasso.get().load(card?.url).placeholder(R.drawable.pokemon_card_back).into(view.cardImageView)
        view.cardImageView.setOnClickListener{
            if(wasPreviousScreenUserDetail!!) {
                listener!!.popBackStack()
            } else {
                listener!!.goBackFromFullScreenToAllCardsFragment()
            }
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ReplaceFragmentListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement ReplaceFragmentListener")
        }
    }

    override fun onDetach() {

        listener!!.setUpBackButton(false)
        listener!!.setDrawerEnabled(true)
        super.onDetach()
        listener = null
    }


    companion object {
        fun newInstance(card: Card, boolean: Boolean) =
            FullScreenCardFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("card", card)
                    putBoolean("wasPreviousScreenUserDetail", boolean)
                }
            }
    }
}

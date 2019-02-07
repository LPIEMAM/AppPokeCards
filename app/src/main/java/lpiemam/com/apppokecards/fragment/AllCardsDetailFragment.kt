package lpiemam.com.apppokecards.fragment


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_all_cards_detail.*
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.ReplaceFragmentListener
import lpiemam.com.apppokecards.model.Card
import lpiemam.com.apppokecards.model.Manager


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class AllCardsDetailFragment : androidx.fragment.app.Fragment() {

    lateinit var card: Card
    var replaceFragmentListener: ReplaceFragmentListener? = null

    companion object {

        fun newInstance(): AllCardsDetailFragment {
            return AllCardsDetailFragment()
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

        replaceFragmentListener!!.setUpBackButton(false)
        replaceFragmentListener!!.setDrawerEnabled(true)
        replaceFragmentListener = null
        super.onDetach()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_all_cards_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        replaceFragmentListener!!.setDrawerEnabled(false)
        replaceFragmentListener!!.setUpBackButton(true)

        userDust.text = Manager.userSiam.dusts.toString()
        allCardsDetailDust.text = card.costDustToCraft.toString()
        allCardsDetailCardVersion.text = card.version
        allCardsDetailPokedexNumber.text = card.pokemon.pokedexNumber.toString()
        allCardsDetailPokemonDescription.text = card.description
        allCardsDetailPokemonName.text = card.pokemon.name
        allCardsDetailPokemonType.text = card.pokemon.type
        Picasso.get().load(card.url).placeholder(R.drawable.pokemon_card_back).into(allCardsDetailImageViewCard)


        allCardsDetailButtonDust.setOnClickListener {
            Manager.userSiam.dusts -= card.costDustToCraft
            Manager.userSiam.userCardList.add(card)
            replaceFragmentListener!!.replaceWithAllCardsFragment()
        }
    }

}

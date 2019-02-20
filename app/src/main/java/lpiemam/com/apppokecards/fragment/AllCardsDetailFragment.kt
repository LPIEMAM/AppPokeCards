package lpiemam.com.apppokecards.fragment


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_all_cards_detail.*
import lpiemam.com.apppokecards.MainActivity
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.ReplaceFragmentListener
import lpiemam.com.apppokecards.model.Card
import lpiemam.com.apppokecards.model.User
import lpiemam.com.apppokecards.viewmodel.ViewModelPokemon


/**
 * A simple [Fragment] subclass.
 *
 */
class AllCardsDetailFragment : Fragment() {

    lateinit var viewModelPokemon: ViewModelPokemon

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


        viewModelPokemon = ViewModelProviders.of(activity!!).get(ViewModelPokemon::class.java)


        super.onViewCreated(view, savedInstanceState)

        (context as MainActivity).supportActionBar!!.show()

        replaceFragmentListener!!.setDrawerEnabled(false)
        replaceFragmentListener!!.setUpBackButton(true)

        userDust.text = User.dusts.toString()
        allCardsDetailDust.text = card.costDustToCraft.toString()
        allCardsDetailCardVersion.text = card.version
        allCardsDetailPokedexNumber.text = card.pokemon.pokedexNumber.toString()
        allCardsDetailPokemonDescription.text = card.description
        allCardsDetailPokemonName.text = card.pokemon.name
        allCardsDetailPokemonType.text = card.pokemon.type
        Picasso.get().load(card.url).placeholder(R.drawable.pokemon_card_back).into(allCardsDetailImageViewCard)


        allCardsDetailButtonDust.setOnClickListener {
            if(User.dusts >= card.costDustToCraft) {
                User.dusts -= card.costDustToCraft
                viewModelPokemon.addUserCard(card)
                replaceFragmentListener!!.replaceWithFullScreenCard(card, false)
            } else {
                val snackbar = Snackbar.make(view, "Vous n'avez pas assez de poussières.", Snackbar.LENGTH_SHORT)
                snackbar.show()
            }
        }
    }

}

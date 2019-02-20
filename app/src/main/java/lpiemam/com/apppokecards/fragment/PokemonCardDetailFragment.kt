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
import kotlinx.android.synthetic.main.fragment_pokemon_card_detail.*
import lpiemam.com.apppokecards.MainActivity
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.ReplaceFragmentListener
import lpiemam.com.apppokecards.model.PokemonCard
import lpiemam.com.apppokecards.model.User
import lpiemam.com.apppokecards.viewmodel.PokemonCardsViewModel


/**
 * A simple [Fragment] subclass.
 *
 */
class PokemonCardDetailFragment : Fragment() {

    lateinit var pokemonCardsViewModel: PokemonCardsViewModel

    lateinit var pokemonCard: PokemonCard
    var replaceFragmentListener: ReplaceFragmentListener? = null

    companion object {

        fun newInstance(): PokemonCardDetailFragment {
            return PokemonCardDetailFragment()
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

        return inflater.inflate(R.layout.fragment_pokemon_card_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        pokemonCardsViewModel = ViewModelProviders.of(activity!!).get(PokemonCardsViewModel::class.java)


        super.onViewCreated(view, savedInstanceState)

        (context as MainActivity).supportActionBar!!.show()

        replaceFragmentListener!!.setDrawerEnabled(false)
        replaceFragmentListener!!.setUpBackButton(true)

        userDust.text = User.dusts.toString()
//        pokemonCard.costDustToCraft = 350
        allCardsDetailDust.text = pokemonCard.getCostToCraft().toString()
//        allCardsDetailCardVersion.text = pokemonCard.version
//        allCardsDetailPokedexNumber.text = pokemonCard.pokemon.pokedexNumber.toString()
//        allCardsDetailPokemonDescription.text = pokemonCard.description
//        allCardsDetailPokemonName.text = pokemonCard.pokemon.name
//        allCardsDetailPokemonType.text = pokemonCard.pokemon.type
        Picasso.get().load(pokemonCard.imageUrlHiRes).placeholder(R.drawable.pokemon_card_back).into(allCardsDetailImageViewCard)


        allCardsDetailButtonDust.setOnClickListener {
            if(User.dusts >= pokemonCard.getCostToCraft()) {
                User.dusts -= pokemonCard.getCostToCraft()
                pokemonCardsViewModel.addUserCard(pokemonCard)
                replaceFragmentListener!!.replaceWithFullScreenCard(pokemonCard, false)
            } else {
                val snackbar = Snackbar.make(view, "Vous n'avez pas assez de poussières.", Snackbar.LENGTH_SHORT)
                snackbar.show()
            }
        }
    }

}

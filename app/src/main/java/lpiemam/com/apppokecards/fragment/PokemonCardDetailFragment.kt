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
import lpiemam.com.apppokecards.MainActivityListener
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
    var mainActivityListener: MainActivityListener? = null

    companion object {

        fun newInstance(pokemonCard: PokemonCard): PokemonCardDetailFragment {
            val pokemonCardDetailFragment = PokemonCardDetailFragment()
            val args = Bundle()
            args.putParcelable("pokeCard", pokemonCard)
            pokemonCardDetailFragment.arguments = args
            return pokemonCardDetailFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pokemonCard = it.getParcelable("pokeCard")!!
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
        // Inflate the layout for this fragment

        mainActivityListener!!.setFragmentTitle(pokemonCard.name)
        return inflater.inflate(R.layout.fragment_pokemon_card_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        pokemonCardsViewModel = ViewModelProviders.of(activity!!).get(PokemonCardsViewModel::class.java)


        super.onViewCreated(view, savedInstanceState)

        (context as MainActivity).supportActionBar!!.show()

        mainActivityListener!!.setDrawerEnabled(false)
        mainActivityListener!!.setUpBackButton(true)

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
                mainActivityListener!!.replaceWithFullScreenCard(pokemonCard, false)
            } else {
                val snackbar = Snackbar.make(view, "Vous n'avez pas assez de poussières.", Snackbar.LENGTH_SHORT)
                snackbar.show()
            }
        }
    }

}

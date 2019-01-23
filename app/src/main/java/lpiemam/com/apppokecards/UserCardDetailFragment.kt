package lpiemam.com.apppokecards


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_user_card_detail.*
import lpiemam.com.apppokecards.model.GlideApp
import lpiemam.com.apppokecards.model.Pokemon
import lpiemam.com.carousel.CarouselView
import java.util.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class UserCardDetailFragment : Fragment() {

    lateinit var pokemon: Pokemon
    var replaceFragmentListener: ReplaceFragmentListener? = null

    companion object {

        fun newInstance(): UserCardDetailFragment {
            return UserCardDetailFragment()
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

        return inflater.inflate(R.layout.fragment_user_card_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        replaceFragmentListener!!.setDrawerEnabled(false)
        replaceFragmentListener!!.setUpBackButton(true)

        userCardDetailCardVersion.text = pokemon.pokemonCardsList[0].version
        userCardDetailPokedexNumber.text = pokemon.pokedexNumber.toString()
        userCardDetailPokemonDescription.text = pokemon.pokemonCardsList[0].description
        userCardDetailPokemonName.text = pokemon.name
        userCardDetailPokemonType.text = pokemon.type
        GlideApp.with(view).load(pokemon.pokemonCardsList[0].url).placeholder(R.drawable.pokemon_card_back).into(userCardDetailImageViewCard)


    }

    override fun onResume() {


        super.onResume()
    }
}

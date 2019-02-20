package lpiemam.com.apppokecards.fragment


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_user_card_detail.*
import lpiemam.com.apppokecards.MainActivity
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.ReplaceFragmentListener
import lpiemam.com.apppokecards.model.User
import lpiemam.com.apppokecards.viewmodel.PokemonCardsViewModel
import lpiemam.com.apppokecards.model.UserCard


/**
 * A simple [Fragment] subclass.
 *
 */
class UserCardDetailFragment : Fragment() {

    lateinit var pokemonCardsViewModel: PokemonCardsViewModel

    lateinit var userCard: UserCard
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


        pokemonCardsViewModel = ViewModelProviders.of(activity!!).get(PokemonCardsViewModel::class.java)

        super.onViewCreated(view, savedInstanceState)
        (context as MainActivity).supportActionBar!!.show()
        replaceFragmentListener!!.setDrawerEnabled(false)
        replaceFragmentListener!!.setUpBackButton(true)

        userDust.text = User.dusts.toString()
        userCardDetailDust.text = userCard.pokemonCard.getCostForDecraft().toString()
//        userCardDetailCardVersion.text = userCard.pokemonCard.version
//        userCardDetailPokedexNumber.text = userCard.pokemonCard.pokemon.pokedexNumber.toString()
//        userCardDetailPokemonDescription.text = userCard.pokemonCard.description
//        userCardDetailPokemonName.text = userCard.pokemonCard.pokemon.name
//        userCardDetailPokemonType.text = userCard.pokemonCard.pokemon.type
        Picasso.get().load(userCard.pokemonCard.imageUrlHiRes).placeholder(R.drawable.pokemon_card_back).into(userCardDetailImageViewCard)
        userCardDetailImageViewCard.setOnClickListener{
            replaceFragmentListener!!.replaceWithFullScreenCard(userCard.pokemonCard, true)
        }


        userCardDetailButtonDust.setOnClickListener {
            User.dusts += userCard.pokemonCard.getCostForDecraft()
            pokemonCardsViewModel.removeUserCard(userCard)
            replaceFragmentListener!!.replaceWithCollectionFragment()
        }
    }

}

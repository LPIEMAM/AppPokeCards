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
import lpiemam.com.apppokecards.MainActivityListener
import lpiemam.com.apppokecards.model.PokemonCard
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
    var mainActivityListener: MainActivityListener? = null

    companion object {

        fun newInstance(userCard: UserCard): UserCardDetailFragment {
            val userCardDetailFragment = UserCardDetailFragment()
            val args = Bundle()
            args.putParcelable("userCard", userCard)
            userCardDetailFragment.arguments = args
            return userCardDetailFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userCard = it.getParcelable("userCard")!!
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

        mainActivityListener!!.setFragmentTitle(userCard.pokemonCard.name)
        return inflater.inflate(R.layout.fragment_user_card_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        pokemonCardsViewModel = ViewModelProviders.of(activity!!).get(PokemonCardsViewModel::class.java)

        super.onViewCreated(view, savedInstanceState)
        mainActivityListener!!.setDrawerEnabled(false)
        mainActivityListener!!.setUpBackButton(true)

        userDust.text = User.dusts.toString()
        userCardDetailDust.text = userCard.pokemonCard.getCostForDecraft().toString()
//        userCardDetailCardVersion.text = userCard.pokemonCard.version
//        userCardDetailPokedexNumber.text = userCard.pokemonCard.pokemon.pokedexNumber.toString()
//        userCardDetailPokemonDescription.text = userCard.pokemonCard.description
//        userCardDetailPokemonName.text = userCard.pokemonCard.pokemon.name
//        userCardDetailPokemonType.text = userCard.pokemonCard.pokemon.type
        Picasso.get().load(userCard.pokemonCard.imageUrlHiRes).placeholder(R.drawable.pokemon_card_back).into(userCardDetailImageViewCard)
        userCardDetailImageViewCard.setOnClickListener{
            mainActivityListener!!.replaceWithFullScreenCard(userCard.pokemonCard, true)
        }


        userCardDetailButtonDust.setOnClickListener {
            User.dusts += userCard.pokemonCard.getCostForDecraft()
            pokemonCardsViewModel.removeUserCard(userCard)
            mainActivityListener!!.replaceWithCollectionFragment()
        }
    }

}

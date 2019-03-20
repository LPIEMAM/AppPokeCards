package lpiemam.com.apppokecards.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_pokemon_card_detail.*
import kotlinx.android.synthetic.main.fragment_user_card_detail.*
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.model.UserCard
import lpiemam.com.apppokecards.model.UserManager
import lpiemam.com.apppokecards.viewmodel.PokemonCardsViewModel


/**
 * A simple [Fragment] subclass.
 *
 */
class UserCardDetailFragment : BaseFragment() {

    var user = UserManager.user

    lateinit var pokemonCardsViewModel: PokemonCardsViewModel

    lateinit var userCard: UserCard

    companion object {

        fun newInstance(userCard: UserCard): UserCardDetailFragment {
            val userCardDetailFragment = UserCardDetailFragment()
            val args = Bundle()
            args.putParcelable("userCard", userCard)
            userCardDetailFragment.arguments = args
            return userCardDetailFragment
        }
    }

    override fun onResume() {
        showActionBar(true)
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userCard = it.getParcelable("userCard")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        setFragmentTitle(userCard.pokemonCard.name)

        return inflater.inflate(R.layout.fragment_user_card_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        pokemonCardsViewModel = ViewModelProviders.of(activity!!).get(PokemonCardsViewModel::class.java)

        setDrawerEnabled(false)
        setUpBackButton(true)

        userDust.text = user?.dusts.toString()
        userCardDetailDust.text = userCard.pokemonCard.getCostForDecraft().toString()
        Picasso.get().load(userCard.pokemonCard.imageUrlHiRes).placeholder(R.drawable.pokemon_card_back)
            .into(userCardDetailImageViewCard)
        userCardDetailImageViewCard.setOnClickListener {
            mainActivityListener?.replaceWithFullScreenCard(userCard.pokemonCard, true)
        }

        userCardDetailButtonDust.setOnClickListener {
            pokemonCardsViewModel.removeUserCard(userCard)
            mainActivityListener?.replaceWithCollectionFragment()
        }

            userCardDetailPokemonName.text = userCard.pokemonCard.name

        if (userCard.pokemonCard.nationalPokedexNumber.toString() != "null" ) {
            userCardDetailPokedexNumber.text = userCard.pokemonCard.nationalPokedexNumber.toString()
            userDetailWithPokedexNumber.visibility = View.VISIBLE
        } else{
            userDetailWithPokedexNumber.visibility = View.GONE
        }

        if (userCard.pokemonCard.rarity != "") {
            userCardRarity.text = userCard.pokemonCard.rarity
            userDetailWithRarity.visibility = View.VISIBLE
        } else{
            userDetailWithRarity.visibility = View.GONE
        }

        if (userCard.pokemonCard.artist != "") {
            userCardArtist.text = userCard.pokemonCard.artist
            userDetailWithArtist.visibility = View.VISIBLE
        } else {
            userDetailWithArtist.visibility = View.GONE
        }
    }

}

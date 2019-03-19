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
import lpiemam.com.apppokecards.MainActivityListener
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.model.PokemonCard
import lpiemam.com.apppokecards.model.UserManager
import lpiemam.com.apppokecards.viewmodel.PokemonCardsViewModel


/**
 * A simple [Fragment] subclass.
 *
 */
class PokemonCardDetailFragment : BaseFragment() {

    lateinit var pokemonCardsViewModel: PokemonCardsViewModel

    lateinit var pokemonCard: PokemonCard
    var user = UserManager.user

    companion object {

        fun newInstance(pokemonCard: PokemonCard): PokemonCardDetailFragment {
            val pokemonCardDetailFragment = PokemonCardDetailFragment()
            val args = Bundle()
            args.putParcelable("pokeCard", pokemonCard)
            pokemonCardDetailFragment.arguments = args
            return pokemonCardDetailFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        setFragmentTitle(pokemonCard.name)

        return inflater.inflate(R.layout.fragment_pokemon_card_detail, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivityListener = context as? MainActivityListener
        if (mainActivityListener == null) {
            throw ClassCastException("$context must implement MainActivityListener")
        }
        arguments?.let {
            pokemonCard = it.getParcelable("pokeCard")!!
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        pokemonCardsViewModel = ViewModelProviders.of(activity!!).get(PokemonCardsViewModel::class.java)


        super.onViewCreated(view, savedInstanceState)

        showActionBar(true)
        setDrawerEnabled(false)
        setUpBackButton(true)

        userDust.text = user?.dusts.toString()
        allCardsDetailDust.text = pokemonCard.getCostToCraft().toString()

        Picasso.get().load(pokemonCard.imageUrlHiRes).placeholder(R.drawable.pokemon_card_back)
            .into(allCardsDetailImageViewCard)


        allCardsDetailButtonDust.setOnClickListener {
            if (user?.dusts!! >= pokemonCard.getCostToCraft()) {
                user!!.dusts -= pokemonCard.getCostToCraft()
                pokemonCardsViewModel.addUserCard(pokemonCard)
                mainActivityListener?.replaceWithFullScreenCard(pokemonCard, false)
            } else {
                val snackbar = Snackbar.make(view, "Vous n'avez pas assez de poussières.", Snackbar.LENGTH_SHORT)
                snackbar.show()
            }
        }
    }

}

package lpiemam.com.apppokecards.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_full_screen_card.view.*
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.model.PokemonCard


class FullScreenCardFragment : BaseFragment() {
    private var pokemonCard: PokemonCard? = null
    private var wasPreviousScreenUserDetail: Boolean? = null

    companion object {
        fun newInstance(pokemonCard: PokemonCard, boolean: Boolean): FullScreenCardFragment {
            val fullScreenCardFragment = FullScreenCardFragment()
            val args = Bundle()
            args.putParcelable("pokemonCard", pokemonCard)
            args.putBoolean("wasPreviousScreenUserDetail", boolean)
            fullScreenCardFragment.arguments = args
            return fullScreenCardFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pokemonCard = it.getParcelable("pokemonCard") as PokemonCard
            wasPreviousScreenUserDetail = it.getBoolean("wasPreviousScreenUserDetail")
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

        showActionBar(false)

        Picasso.get().load(pokemonCard?.imageUrlHiRes).placeholder(R.drawable.pokemon_card_back)
            .into(view.cardImageView)

        view.cardImageView.setOnClickListener {
            mainActivityListener?.showActionBar(true)
            if (wasPreviousScreenUserDetail!!) {
                mainActivityListener?.popBackStack()
            } else {
                mainActivityListener?.goBackFromFullScreenToAllCardsFragment()
            }
        }
    }
}

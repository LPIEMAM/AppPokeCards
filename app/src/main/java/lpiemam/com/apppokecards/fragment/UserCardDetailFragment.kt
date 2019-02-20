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
import lpiemam.com.apppokecards.viewmodel.ViewModelPokemon
import lpiemam.com.apppokecards.model.UserCard


/**
 * A simple [Fragment] subclass.
 *
 */
class UserCardDetailFragment : Fragment() {

    lateinit var viewModelPokemon: ViewModelPokemon

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


        viewModelPokemon = ViewModelProviders.of(activity!!).get(ViewModelPokemon::class.java)

        super.onViewCreated(view, savedInstanceState)
        (context as MainActivity).supportActionBar!!.show()
        replaceFragmentListener!!.setDrawerEnabled(false)
        replaceFragmentListener!!.setUpBackButton(true)

        userDust.text = User.dusts.toString()
        userCardDetailDust.text = userCard.card.dustGivenByDecraft.toString()
        userCardDetailCardVersion.text = userCard.card.version
        userCardDetailPokedexNumber.text = userCard.card.pokemon.pokedexNumber.toString()
        userCardDetailPokemonDescription.text = userCard.card.description
        userCardDetailPokemonName.text = userCard.card.pokemon.name
        userCardDetailPokemonType.text = userCard.card.pokemon.type
        Picasso.get().load(userCard.card.url).placeholder(R.drawable.pokemon_card_back).into(userCardDetailImageViewCard)
        userCardDetailImageViewCard.setOnClickListener{
            replaceFragmentListener!!.replaceWithFullScreenCard(userCard.card, true)
        }


        userCardDetailButtonDust.setOnClickListener {
            User.dusts += userCard.card.dustGivenByDecraft
            viewModelPokemon.removeUserCard(userCard)
            replaceFragmentListener!!.replaceWithCollectionFragment()
        }
    }

}

package lpiemam.com.apppokecards.fragment

import android.app.AlertDialog
import android.app.VoiceInteractor
import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_trade.*

import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.model.Trade
import lpiemam.com.apppokecards.model.UserManager
import lpiemam.com.apppokecards.viewmodel.PokemonCardsViewModel
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TradeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [TradeFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class TradeFragment : BaseFragment() {


    lateinit var pokemonCardsViewModel: PokemonCardsViewModel
    var currentTrade: Trade? = null

    companion object {
        fun newInstance() =
            TradeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        setFragmentTitle("Trade")

        pokemonCardsViewModel = ViewModelProviders.of(activity!!).get(PokemonCardsViewModel::class.java)

        pokemonCardsViewModel.currentTradeForUser.observe(this, Observer {
            if(it != null) {
                showPopUp(it)
            }
        })

        pokemonCardsViewModel.getCurrentTradeForUser(UserManager.user!!)

        return inflater.inflate(R.layout.fragment_trade, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpVisibility()
        setUpButtons()
//        showPopUp()


        if(pokemonCardsViewModel.selectedUserCardForTrade != null) {
            Picasso.get().load(pokemonCardsViewModel.selectedUserCardForTrade!!.pokemonCard.imageUrlHiRes).placeholder(R.drawable.pokemon_card_back).into(tradeUserCard1ImageView)
        }
        if(pokemonCardsViewModel.selectedTrade != null) {
            Picasso.get().load(pokemonCardsViewModel.selectedTrade!!.userCard1!!.pokemonCard.imageUrlHiRes).placeholder(R.drawable.pokemon_card_back).into(tradeUserCard2ImageView)
        }

    }

    private fun setUpVisibility() {
        if(pokemonCardsViewModel.selectedTrade != null) {
            validateButton.text = "Trade"
            chooseACardButton.visibility = View.VISIBLE
            tradeToggleButton.isChecked = true
        } else {
            validateButton.text = "Offer"
            chooseACardButton.visibility = View.GONE
            tradeToggleButton.isChecked = false
        }
    }

    private fun setUpButtons() {
        pickYourCardButton.setOnClickListener {
            mainActivityListener?.replaceWithPickCardFragment()
        }

        validateButton.setOnClickListener {
            if(!tradeToggleButton.isChecked) {
                if(pokemonCardsViewModel.selectedUserCardForTrade != null) {
                    pokemonCardsViewModel.tradeList.add(Trade(user1 = UserManager.user!!, user2 = null, userCard1 = pokemonCardsViewModel.selectedUserCardForTrade, userCard2 = null))
                    pokemonCardsViewModel.selectedUserCardForTrade = null
                }
            } else {
                if(pokemonCardsViewModel.selectedUserCardForTrade != null && pokemonCardsViewModel.selectedTrade != null) {
                    pokemonCardsViewModel.selectedTrade!!.user2 = UserManager.user!!
                    pokemonCardsViewModel.selectedTrade!!.userCard2 = pokemonCardsViewModel.selectedUserCardForTrade
                    pokemonCardsViewModel.selectedTrade = null
                    pokemonCardsViewModel.selectedUserCardForTrade = null
                }
            }
            chooseACardButton.visibility = View.GONE
            pickYourCardButton.visibility = View.GONE
            validateButton.visibility = View.GONE
            acceptRefuseTradeGroup.visibility = View.GONE
            tradeToggleButton.isEnabled = false
            Log.d("ValidateButton", pokemonCardsViewModel.selectedTrade.toString())
//            Timber.d("ValidateButton %s ", pokemonCardsViewModel.selectedTrade.toString())
        }

        tradeToggleButton.setOnClickListener {
            chooseACardButton.visibility = if (tradeToggleButton.isChecked) View.VISIBLE else View.GONE
            validateButton.text = if (tradeToggleButton.isChecked) "Trade" else "Offer"
        }


        chooseACardButton.setOnClickListener {
            mainActivityListener?.replaceWithChooseCardFragment()
        }

        acceptTradeButton.setOnClickListener {
            //TODO Appel API pour set le trade à "validé"
            //TODO Appel API pour échanger les cartes

            pokemonCardsViewModel.userCardList.remove(currentTrade?.userCard1)
            pokemonCardsViewModel.userCardList.add(currentTrade?.userCard2!!)

            Picasso.get().load(R.drawable.pokemon_card_back).placeholder(R.drawable.pokemon_card_back).into(tradeUserCard1ImageView)
            Picasso.get().load(R.drawable.pokemon_card_back).placeholder(R.drawable.pokemon_card_back).into(tradeUserCard2ImageView)

            currentTrade?.isValidated = true

            tradeToggleButton.isChecked = false
            pickYourCardButton.visibility = View.VISIBLE
            chooseACardButton.visibility = View.GONE
            validateButton.visibility = View.VISIBLE
            acceptRefuseTradeGroup.visibility = View.GONE
            doneButton.visibility = View.GONE
            tradeToggleButton.isEnabled = true

        }

        refuseTradeButton.setOnClickListener {
            //TODO Appel API pour set le trade à "refusé"
            currentTrade?.isValidated = false

            Picasso.get().load(R.drawable.pokemon_card_back).placeholder(R.drawable.pokemon_card_back).into(tradeUserCard1ImageView)
            Picasso.get().load(R.drawable.pokemon_card_back).placeholder(R.drawable.pokemon_card_back).into(tradeUserCard2ImageView)

            tradeToggleButton.isChecked = false
            pickYourCardButton.visibility = View.VISIBLE
            chooseACardButton.visibility = View.GONE
            validateButton.visibility = View.VISIBLE
            acceptRefuseTradeGroup.visibility = View.GONE
            doneButton.visibility = View.GONE
            tradeToggleButton.isEnabled = true

        }

        doneButton.setOnClickListener {
            Picasso.get().load(R.drawable.pokemon_card_back).placeholder(R.drawable.pokemon_card_back).into(tradeUserCard1ImageView)
            Picasso.get().load(R.drawable.pokemon_card_back).placeholder(R.drawable.pokemon_card_back).into(tradeUserCard2ImageView)

            //TODO appel API pour set le trade à "traité"


            tradeToggleButton.isChecked = false
            pickYourCardButton.visibility = View.VISIBLE
            chooseACardButton.visibility = View.GONE
            validateButton.visibility = View.VISIBLE
            acceptRefuseTradeGroup.visibility = View.GONE
            doneButton.visibility = View.GONE
            tradeToggleButton.isEnabled = true

        }
    }

    private fun showPopUp(currentTrade : Trade) {

        if (currentTrade.user1.nickName == UserManager.user!!.nickName) {

            val builder = AlertDialog.Builder(context, R.style.AlertDialogCustom)

            if(currentTrade.user2 == null) {
                builder.setTitle("Ongoing Trade")

                builder.setMessage("You have a trade going.")

                Picasso.get().load(currentTrade.userCard1!!.pokemonCard.imageUrlHiRes).placeholder(R.drawable.pokemon_card_back).into(tradeUserCard1ImageView)

                tradeToggleButton.isChecked = false
                chooseACardButton.visibility = View.GONE
                pickYourCardButton.visibility = View.GONE
                validateButton.visibility = View.GONE
                acceptRefuseTradeGroup.visibility = View.GONE
                tradeToggleButton.isEnabled = false


            } else {
                builder.setTitle("Trading")

                builder.setMessage("Someone has an offer for you.")
                Picasso.get().load(currentTrade.userCard1!!.pokemonCard.imageUrlHiRes).placeholder(R.drawable.pokemon_card_back).into(tradeUserCard1ImageView)
                Picasso.get().load(currentTrade.userCard2!!.pokemonCard.imageUrlHiRes).placeholder(R.drawable.pokemon_card_back).into(tradeUserCard2ImageView)

                tradeToggleButton.isChecked = false
                chooseACardButton.visibility = View.GONE
                pickYourCardButton.visibility = View.GONE
                validateButton.visibility = View.GONE
                acceptRefuseTradeGroup.visibility = View.VISIBLE
                tradeToggleButton.isEnabled = false
            }

            builder.setPositiveButton("OK"){_,_ ->
                Toast.makeText(context,"You cancelled the dialog.",Toast.LENGTH_SHORT).show()
            }

            // Finally, make the alert dialog using builder
            val dialog: AlertDialog = builder.create()

            // Display the alert dialog on app interface
            dialog.show()
        } else {
            val builder = AlertDialog.Builder(context, R.style.AlertDialogCustom)

            when {
                currentTrade.isValidated == true -> {
                    builder.setTitle("Trade accepted")

                    builder.setMessage("Your trade has been accepted, your new card made it to your collection.")
                    Picasso.get().load(currentTrade.userCard1!!.pokemonCard.imageUrlHiRes).placeholder(R.drawable.pokemon_card_back).into(tradeUserCard2ImageView)
                    Picasso.get().load(currentTrade.userCard2!!.pokemonCard.imageUrlHiRes).placeholder(R.drawable.pokemon_card_back).into(tradeUserCard1ImageView)

                    tradeToggleButton.isChecked = true
                    chooseACardButton.visibility = View.GONE
                    pickYourCardButton.visibility = View.GONE
                    validateButton.visibility = View.GONE
                    acceptRefuseTradeGroup.visibility = View.GONE
                    doneButton.visibility = View.VISIBLE
                    tradeToggleButton.isEnabled = false

                }
                currentTrade.isValidated == false -> {
                    builder.setTitle("Trade refused")

                    builder.setMessage("Your trade has been refused, you card is back in your collection.")
                    Picasso.get().load(currentTrade.userCard1!!.pokemonCard.imageUrlHiRes).placeholder(R.drawable.pokemon_card_back).into(tradeUserCard2ImageView)
                    Picasso.get().load(currentTrade.userCard2!!.pokemonCard.imageUrlHiRes).placeholder(R.drawable.pokemon_card_back).into(tradeUserCard1ImageView)

                    tradeToggleButton.isChecked = true
                    chooseACardButton.visibility = View.GONE
                    pickYourCardButton.visibility = View.GONE
                    validateButton.visibility = View.GONE
                    acceptRefuseTradeGroup.visibility = View.GONE
                    doneButton.visibility = View.VISIBLE
                    tradeToggleButton.isEnabled = false
                }
                else -> {
                    builder.setTitle("OngoingTrade")

                    builder.setMessage("You have a trade going.")
                    Picasso.get().load(currentTrade.userCard1!!.pokemonCard.imageUrlHiRes).placeholder(R.drawable.pokemon_card_back).into(tradeUserCard2ImageView)
                    Picasso.get().load(currentTrade.userCard2!!.pokemonCard.imageUrlHiRes).placeholder(R.drawable.pokemon_card_back).into(tradeUserCard1ImageView)

                    tradeToggleButton.isChecked = true
                    chooseACardButton.visibility = View.GONE
                    pickYourCardButton.visibility = View.GONE
                    validateButton.visibility = View.GONE
                    acceptRefuseTradeGroup.visibility = View.GONE
                    tradeToggleButton.isEnabled = false
                }
            }

            builder.setPositiveButton("OK"){_,_ ->
                Toast.makeText(context,"You cancelled the dialog.",Toast.LENGTH_SHORT).show()
            }

            // Finally, make the alert dialog using builder
            val dialog: AlertDialog = builder.create()

            // Display the alert dialog on app interface
            dialog.show()
        }
    }
}

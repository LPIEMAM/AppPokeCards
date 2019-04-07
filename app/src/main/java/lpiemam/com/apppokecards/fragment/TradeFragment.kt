package lpiemam.com.apppokecards.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_trade.*
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.model.Trade
import lpiemam.com.apppokecards.model.UserManager
import lpiemam.com.apppokecards.viewmodel.PokemonCardsViewModel


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

        setUpBackButton(false)
        setDrawerEnabled(true)
        setFragmentTitle("Trade")

        pokemonCardsViewModel = ViewModelProviders.of(activity!!).get(PokemonCardsViewModel::class.java)

        pokemonCardsViewModel.currentTradeForUser.observe(this, Observer {
            loadingGroup.visibility = View.VISIBLE
            tradeProgressBar.visibility = View.GONE
            if (it != null) {
                showPopUp(it)
            } else {
                setFragmentTitle("Offer a Trade")
                acceptRefuseTradeGroup.visibility = View.GONE
                doneButton.visibility = View.GONE
                pickYourCardButton.visibility = View.VISIBLE
                validateButton.visibility = View.VISIBLE
            }
            pokemonCardsViewModel.currentTradeForUser = MutableLiveData()
        })


        return inflater.inflate(R.layout.fragment_trade, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pokemonCardsViewModel.getCurrentTradeForUser(UserManager.loggedUser!!)

        setUpVisibility()
        setUpButtons()


        if (pokemonCardsViewModel.selectedUserCardForTrade != null) {
            Picasso.get().load(pokemonCardsViewModel.selectedUserCardForTrade!!.pokemonCard.imageUrlHiRes)
                .placeholder(R.drawable.pokemon_card_back).into(tradeUserCard1ImageView)
        }
        if (pokemonCardsViewModel.selectedTrade != null) {
            Picasso.get().load(pokemonCardsViewModel.selectedTrade!!.userCard1!!.pokemonCard.imageUrlHiRes)
                .placeholder(R.drawable.pokemon_card_back).into(tradeUserCard2ImageView)
        }

    }

    private fun setUpVisibility() {
        if (pokemonCardsViewModel.userLookingForTrade) {
            validateButton.text = "Trade"
            setFragmentTitle("Find a Trade")
            chooseACardButton.visibility = View.VISIBLE
        } else {
            validateButton.text = "Offer"
            setFragmentTitle("Offer a Trade")
            chooseACardButton.visibility = View.GONE
        }
    }

    private fun setUpButtons() {
        pickYourCardButton.setOnClickListener {
            mainActivityListener?.replaceWithPickCardFragment()
        }

        validateButton.setOnClickListener {
            if (!tradeToggleButton.isChecked) {
                if (pokemonCardsViewModel.selectedUserCardForTrade != null) {
                    val newTrade = Trade(
                        user1 = UserManager.loggedUser!!,
                        user2 = null,
                        userCard1 = pokemonCardsViewModel.selectedUserCardForTrade,
                        userCard2 = null
                    )

                    pokemonCardsViewModel.selectedUserCardForTrade!!.numberOfCardAvailable--
                    pokemonCardsViewModel.updateCardInDB(pokemonCardsViewModel.selectedUserCardForTrade!!)
                    pokemonCardsViewModel.selectedUserCardForTrade = null
                    pokemonCardsViewModel.postTrade(newTrade)

                    chooseACardButton.visibility = View.GONE
                    pickYourCardButton.visibility = View.GONE
                    validateButton.visibility = View.GONE
                    acceptRefuseTradeGroup.visibility = View.GONE
                    tradeToggleButton.isEnabled = false
                }
            } else {
                if (pokemonCardsViewModel.selectedUserCardForTrade != null && pokemonCardsViewModel.selectedTrade != null) {
                    pokemonCardsViewModel.selectedTrade!!.user2 = UserManager.loggedUser!!
                    pokemonCardsViewModel.selectedTrade!!.userCard2 = pokemonCardsViewModel.selectedUserCardForTrade
                    pokemonCardsViewModel.patchTrade(pokemonCardsViewModel.selectedTrade!!)
                    pokemonCardsViewModel.selectedUserCardForTrade!!.numberOfCardAvailable--
                    pokemonCardsViewModel.updateCardInDB(pokemonCardsViewModel.selectedUserCardForTrade!!)
                    pokemonCardsViewModel.selectedTrade = null
                    pokemonCardsViewModel.selectedUserCardForTrade = null

                    chooseACardButton.visibility = View.GONE
                    pickYourCardButton.visibility = View.GONE
                    validateButton.visibility = View.GONE
                    acceptRefuseTradeGroup.visibility = View.GONE
                    tradeToggleButton.isEnabled = false
                }
            }
        }

        tradeToggleButton.setOnClickListener {
            chooseACardButton.visibility = if (tradeToggleButton.isChecked) View.VISIBLE else View.GONE
            setFragmentTitle(if (tradeToggleButton.isChecked) "Find a Trade" else "Offer a Trade")
            validateButton.text = if (tradeToggleButton.isChecked) "Trade" else "Offer"
            pokemonCardsViewModel.userLookingForTrade = tradeToggleButton.isChecked
        }


        chooseACardButton.setOnClickListener {
            mainActivityListener?.replaceWithChooseCardFragment()
        }

        acceptTradeButton.setOnClickListener {
            currentTrade?.validated = true
            pokemonCardsViewModel.patchTradeValidated(currentTrade!!)

            acceptRefuseTradeGroup.visibility = View.GONE

        }

        refuseTradeButton.setOnClickListener {
            currentTrade?.validated = false
            pokemonCardsViewModel.patchTradeValidated(currentTrade!!)

            acceptRefuseTradeGroup.visibility = View.GONE

        }

        doneButton.setOnClickListener {
            Picasso.get().load(R.drawable.pokemon_card_back).placeholder(R.drawable.pokemon_card_back)
                .into(tradeUserCard1ImageView)
            Picasso.get().load(R.drawable.pokemon_card_back).placeholder(R.drawable.pokemon_card_back)
                .into(tradeUserCard2ImageView)

            if (currentTrade!!.validated == true) {
                pokemonCardsViewModel.deleteTrade(currentTrade!!)
                pokemonCardsViewModel.affectedRowsUserCardsLiveData = MutableLiveData()
                pokemonCardsViewModel.removeUserCardForUser(currentTrade!!.user1.userId, currentTrade!!.userCard1!!)
                pokemonCardsViewModel.removeUserCardForUser(currentTrade!!.user2!!.userId, currentTrade!!.userCard2!!)
                pokemonCardsViewModel.affectedRowsUserCardsLiveData.observe(this, Observer {
                    if (it == 2) {
                        pokemonCardsViewModel.addUserCardForUser(
                            currentTrade!!.user1.userId,
                            currentTrade!!.userCard2!!.pokemonCard
                        )
                        pokemonCardsViewModel.addUserCardForUser(
                            currentTrade!!.user2!!.userId,
                            currentTrade!!.userCard1!!.pokemonCard
                        )
                        pokemonCardsViewModel.affectedRowsUserCardsLiveData = MutableLiveData()
                    }
                })

            } else {
                currentTrade!!.userCard1!!.numberOfCardAvailable++
                currentTrade!!.userCard2!!.numberOfCardAvailable++
                pokemonCardsViewModel.updateCardInDB(currentTrade!!.userCard1!!)
                pokemonCardsViewModel.updateCardInDB(currentTrade!!.userCard2!!)

            }

            tradeToggleButton.isChecked = false
            pickYourCardButton.visibility = View.VISIBLE
            chooseACardButton.visibility = View.GONE
            validateButton.visibility = View.VISIBLE
            acceptRefuseTradeGroup.visibility = View.GONE
            doneButton.visibility = View.GONE
            tradeToggleButton.isEnabled = true

        }
    }

    private fun showPopUp(currentTrade: Trade) {

        this.currentTrade = currentTrade

        if (currentTrade.user1.userId == UserManager.loggedUser!!.userId) {

            setFragmentTitle("Trade Offer")
            val builder = AlertDialog.Builder(context, R.style.AlertDialogCustom)

            if (currentTrade.validated == true) {
                builder.setTitle("Ongoing Trade")

                builder.setMessage("Waiting for other player to terminate the trade.")

                Picasso.get().load(currentTrade.userCard1!!.pokemonCard.imageUrlHiRes)
                    .placeholder(R.drawable.pokemon_card_back).into(tradeUserCard1ImageView)
                Picasso.get().load(currentTrade.userCard2!!.pokemonCard.imageUrlHiRes)
                    .placeholder(R.drawable.pokemon_card_back).into(tradeUserCard2ImageView)

                tradeToggleButton.isChecked = false
                chooseACardButton.visibility = View.GONE
                pickYourCardButton.visibility = View.GONE
                validateButton.visibility = View.GONE
                acceptRefuseTradeGroup.visibility = View.GONE
                tradeToggleButton.isEnabled = false
            } else {

                if (currentTrade.user2 == null) {
                    builder.setTitle("Ongoing Trade")

                    builder.setMessage("You have a trade going.")

                    Picasso.get().load(currentTrade.userCard1!!.pokemonCard.imageUrlHiRes)
                        .placeholder(R.drawable.pokemon_card_back).into(tradeUserCard1ImageView)

                    tradeToggleButton.isChecked = false
                    chooseACardButton.visibility = View.GONE
                    pickYourCardButton.visibility = View.GONE
                    validateButton.visibility = View.GONE
                    acceptRefuseTradeGroup.visibility = View.GONE
                    tradeToggleButton.isEnabled = false


                } else {
                    builder.setTitle("Trading")

                    builder.setMessage("Someone has an offer for you.")
                    Picasso.get().load(currentTrade.userCard1!!.pokemonCard.imageUrlHiRes)
                        .placeholder(R.drawable.pokemon_card_back).into(tradeUserCard1ImageView)
                    Picasso.get().load(currentTrade.userCard2!!.pokemonCard.imageUrlHiRes)
                        .placeholder(R.drawable.pokemon_card_back).into(tradeUserCard2ImageView)

                    tradeToggleButton.isChecked = false
                    chooseACardButton.visibility = View.GONE
                    pickYourCardButton.visibility = View.GONE
                    validateButton.visibility = View.GONE
                    acceptRefuseTradeGroup.visibility = View.VISIBLE
                    tradeToggleButton.isEnabled = false
                }
            }

            builder.setPositiveButton("OK") { _, _ -> }

            // Finally, make the alert dialog using builder
            val dialog: AlertDialog = builder.create()

            // Display the alert dialog on app interface
            dialog.show()
        } else {
            val builder = AlertDialog.Builder(context, R.style.AlertDialogCustom)

            setFragmentTitle("Trade Answer")
            when {
                currentTrade.validated == true -> {
                    builder.setTitle("Trade accepted")

                    builder.setMessage("Your trade has been accepted, your new card will now make it into your collection.")
                    Picasso.get().load(currentTrade.userCard1!!.pokemonCard.imageUrlHiRes)
                        .placeholder(R.drawable.pokemon_card_back).into(tradeUserCard2ImageView)
                    Picasso.get().load(currentTrade.userCard2!!.pokemonCard.imageUrlHiRes)
                        .placeholder(R.drawable.pokemon_card_back).into(tradeUserCard1ImageView)

                    tradeToggleButton.isChecked = true
                    chooseACardButton.visibility = View.GONE
                    pickYourCardButton.visibility = View.GONE
                    validateButton.visibility = View.GONE
                    acceptRefuseTradeGroup.visibility = View.GONE
                    doneButton.visibility = View.VISIBLE
                    tradeToggleButton.isEnabled = false

                }
                currentTrade.validated == false -> {
                    builder.setTitle("Trade refused")

                    builder.setMessage("Your trade has been refused, you card is back in your collection.")
                    Picasso.get().load(currentTrade.userCard1!!.pokemonCard.imageUrlHiRes)
                        .placeholder(R.drawable.pokemon_card_back).into(tradeUserCard2ImageView)
                    Picasso.get().load(currentTrade.userCard2!!.pokemonCard.imageUrlHiRes)
                        .placeholder(R.drawable.pokemon_card_back).into(tradeUserCard1ImageView)

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
                    Picasso.get().load(currentTrade.userCard1!!.pokemonCard.imageUrlHiRes)
                        .placeholder(R.drawable.pokemon_card_back).into(tradeUserCard2ImageView)
                    Picasso.get().load(currentTrade.userCard2!!.pokemonCard.imageUrlHiRes)
                        .placeholder(R.drawable.pokemon_card_back).into(tradeUserCard1ImageView)

                    tradeToggleButton.isChecked = true
                    chooseACardButton.visibility = View.GONE
                    pickYourCardButton.visibility = View.GONE
                    validateButton.visibility = View.GONE
                    acceptRefuseTradeGroup.visibility = View.GONE
                    tradeToggleButton.isEnabled = false
                }
            }

            builder.setPositiveButton("OK") { _, _ -> }

            // Finally, make the alert dialog using builder
            val dialog: AlertDialog = builder.create()

            // Display the alert dialog on app interface
            dialog.show()
        }
    }
}

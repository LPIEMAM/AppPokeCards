package lpiemam.com.apppokecards.fragment


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_shop.*
import lpiemam.com.apppokecards.*

import lpiemam.com.apppokecards.adapter.ShopAdapter
import lpiemam.com.apppokecards.model.CardsPack
import lpiemam.com.apppokecards.model.PokemonCard
import lpiemam.com.apppokecards.model.User
import lpiemam.com.apppokecards.viewmodel.PokemonCardsViewModel



/**
 * A simple [Fragment] subclass.
 */
class ShopFragment : Fragment() {

    lateinit var pokemonCardsViewModel: PokemonCardsViewModel

    var shopAdapter: ShopAdapter? = null
    var replaceFragmentListener: ReplaceFragmentListener? = null

    companion object {

        fun newInstance(): ShopFragment {
            return ShopFragment()
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

        replaceFragmentListener = null
        super.onDetach()
    }

    override fun onResume() {

        replaceFragmentListener!!.setUpBackButton(false)
        replaceFragmentListener!!.setDrawerEnabled(true)

        for (cardPack in shopAdapter!!.cardsPackList) {
            cardPack.isSelected = false
        }
        shopAdapter!!.notifyDataSetChanged()

        super.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        setHasOptionsMenu(true)


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        pokemonCardsViewModel = ViewModelProviders.of(activity!!).get(PokemonCardsViewModel::class.java)


        shopConstraintLayout.setOnClickListener {
            for (cardPack in shopAdapter!!.cardsPackList) {
                cardPack.isSelected = false
            }
            shopAdapter!!.notifyDataSetChanged()
        }



        userCoinsTextView.text = User.coins.toString()

        setUpRecyclerView()

        buyPackButton.setOnClickListener {
            lateinit var selectedPack: CardsPack
            var onePackSelected = false
            for (pack in shopAdapter!!.cardsPackList) {
                if (pack.isSelected) {
                    selectedPack = pack
                    onePackSelected = true
                }
            }
            if (onePackSelected) {
                if (User.canBuyAPack(selectedPack)) {
                    try {
                        pokemonCardsViewModel.generateRandomCards(selectedPack)

                        pokemonCardsViewModel.pokemonCardsForPackLiveData.observe(this, Observer {

                            var packOpeningDialogFragment = PackOpeningDialogFragment()
                            packOpeningDialogFragment.listCardsPack = ArrayList(selectedPack.listPokemonCards)
                            packOpeningDialogFragment.show(childFragmentManager, "Contenu du Pack")

                            pokemonCardsViewModel.buyAPack(selectedPack)
                            selectedPack.clearCardList()
                        })


                    } catch (e: Exception) {
                        val snackbar = Snackbar.make(view, e.message!!, Snackbar.LENGTH_LONG)
                        snackbar.show()
                    }

                } else {
                    val snackbar = Snackbar.make(view, "Vous n'avez pas suffisament de pièces.", Snackbar.LENGTH_LONG)
                    snackbar.show()
                }
            }
            userCoinsTextView.text = User.coins.toString()
        }



        super.onViewCreated(view, savedInstanceState)
    }




    private fun setUpRecyclerView() {
        shopAdapter = ShopAdapter(ArrayList(pokemonCardsViewModel.getCardsPackList()))

        shopRecyclerView!!.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 3)
        shopRecyclerView!!.adapter = shopAdapter

        shopRecyclerView!!.addOnItemTouchListener(
            RecyclerTouchListener(
                context!!,
                shopRecyclerView!!,
                object : RecyclerTouchListener.ClickListener {
                    override fun onClick(view: View, position: Int) {

                        val temp = shopAdapter!!.cardsPackList[position].isSelected
                        for (cardPack in shopAdapter!!.cardsPackList) {
                            cardPack.isSelected = false
                        }
                        shopAdapter!!.cardsPackList[position].isSelected = !temp
                        shopAdapter!!.notifyDataSetChanged()
                        //val pokemonCard = shopAdapter!!.cardList[position]

                        //replaceFragmentListener!!.replaceWithUserDetailFragment(pokemonCard)
                    }

                    override fun onLongClick(view: View?, position: Int) {
                        val temp = shopAdapter!!.cardsPackList[position].isSelected
                        for (cardPack in shopAdapter!!.cardsPackList) {
                            cardPack.isSelected = false
                        }
                        shopAdapter!!.cardsPackList[position].isSelected = !temp
                        shopAdapter!!.notifyDataSetChanged()
                        //val pokemonCard = userCardAdapter!!.cardList[position]

                        //replaceFragmentListener!!.replaceWithUserDetailFragment(pokemonCard)
                    }
                })
        )
    }
}


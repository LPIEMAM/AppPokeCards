package lpiemam.com.apppokecards.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_shop.*
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.RecyclerTouchListener
import lpiemam.com.apppokecards.adapter.ShopAdapter
import lpiemam.com.apppokecards.model.CardsPack
import lpiemam.com.apppokecards.model.UserManager
import lpiemam.com.apppokecards.viewmodel.PokemonCardsViewModel


/**
 * A simple [Fragment] subclass.
 */
class ShopFragment : BaseFragment() {

    lateinit var pokemonCardsViewModel: PokemonCardsViewModel


    var shopAdapter: ShopAdapter? = null

    companion object {

        fun newInstance(): ShopFragment {
            return ShopFragment()
        }
    }

    override fun onResume() {

        setUpBackButton(false)
        setDrawerEnabled(true)

        for (cardPack in shopAdapter!!.cardsPackList) {
            cardPack.isSelected = false
        }
        shopAdapter?.notifyDataSetChanged()

        super.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setFragmentTitle("Shop")

        //mainActivityListener!!.setFragmentTitle("Shop")

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
            shopAdapter?.notifyDataSetChanged()
        }

        userCoinsTextView.text = UserManager.user?.coins.toString()

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
                if (UserManager.user!!.canBuyAPack(selectedPack)) {
                    try {

                        pokemonCardsViewModel.pokemonCardsForPackLiveData = MutableLiveData()

                        pokemonCardsViewModel.pokemonCardsForPackLiveData.observe(this, Observer {

                            var packOpeningDialogFragment = PackOpeningDialogFragment()
                            packOpeningDialogFragment.listCardsPack = ArrayList(it)
                            packOpeningDialogFragment.show(childFragmentManager, "Pack Content")

                            pokemonCardsViewModel.buyAPack(selectedPack)
                            selectedPack.clearCardList()
                            userCoinsTextView.text = UserManager.user?.coins.toString()
                        })

                        pokemonCardsViewModel.generateRandomCards(selectedPack)


                    } catch (e: Exception) {
                        val snackbar = Snackbar.make(view, e.message!!, Snackbar.LENGTH_LONG)
                        snackbar.show()
                    }

                } else {
                    val snackbar = Snackbar.make(view, "Vous n'avez pas suffisament de pièces.", Snackbar.LENGTH_LONG)
                    snackbar.show()
                }
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }


    private fun setUpRecyclerView() {
        shopAdapter = ShopAdapter(ArrayList(pokemonCardsViewModel.getCardsPackList()))

        shopRecyclerView?.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 3)
        shopRecyclerView?.adapter = shopAdapter

        shopRecyclerView?.addOnItemTouchListener(
            RecyclerTouchListener(
                context!!,
                shopRecyclerView!!,
                object : RecyclerTouchListener.ClickListener {
                    override fun onClick(view: View, position: Int) {

                        val temp = shopAdapter?.cardsPackList!![position].isSelected
                        for (cardPack in shopAdapter!!.cardsPackList) {
                            cardPack.isSelected = false
                        }
                        shopAdapter?.cardsPackList!![position].isSelected = !temp
                        shopAdapter?.notifyDataSetChanged()
                    }

                    override fun onLongClick(view: View?, position: Int) {
                        val temp = shopAdapter?.cardsPackList!![position].isSelected
                        for (cardPack in shopAdapter!!.cardsPackList) {
                            cardPack.isSelected = false
                        }
                        shopAdapter?.cardsPackList!![position].isSelected = !temp
                        shopAdapter?.notifyDataSetChanged()
                    }
                })
        )
    }
}


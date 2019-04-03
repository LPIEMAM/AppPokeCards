package lpiemam.com.apppokecards.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_trade_list.*
import kotlinx.android.synthetic.main.fragment_user_cards.*
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.RecyclerTouchListener
import lpiemam.com.apppokecards.adapter.TradeAdapter
import lpiemam.com.apppokecards.adapter.UserCardsAdapter
import lpiemam.com.apppokecards.viewmodel.PokemonCardsViewModel
import java.util.ArrayList

class TradeListFragment : BaseFragment() {

    lateinit var pokemonCardsViewModel: PokemonCardsViewModel

    var tradeAdapter: TradeAdapter? = null

    companion object {
        fun newInstance() =
            TradeListFragment().apply {
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


        setHasOptionsMenu(true)
        setFragmentTitle("Choose A Card")

        pokemonCardsViewModel = ViewModelProviders.of(activity!!).get(PokemonCardsViewModel::class.java)

//        pokemonCardsViewModel.userCardListLiveData.observe(this, Observer {
//
//            it.sort()
////            tradeAdapter?.setUpLists(it)
////            pokemonCardsViewModel.userCardList = it
//            tradeAdapter?.notifyDataSetChanged()
//
//        })

        return inflater.inflate(R.layout.fragment_trade_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        tradeFragmentSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                tradeAdapter?.filter?.filter(s)
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                tradeAdapter?.filter?.filter(s)
                return false
            }
        })
    }

    private fun setUpRecyclerView() {
        tradeAdapter = TradeAdapter(ArrayList(pokemonCardsViewModel.tradeList))

        tradeFragmentRecyclerView?.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 4)
        tradeFragmentRecyclerView?.adapter = tradeAdapter

        tradeFragmentRecyclerView?.addOnItemTouchListener(
            RecyclerTouchListener(
                context!!,
                tradeFragmentRecyclerView!!,
                object : RecyclerTouchListener.ClickListener {
                    override fun onClick(view: View, position: Int) {
                        val trade = tradeAdapter?.tradeListFiltered!![position]

                        pokemonCardsViewModel.selectedTrade = trade

                        mainActivityListener?.popBackStack()


                    }

                    override fun onLongClick(view: View?, position: Int) {
                        val trade = tradeAdapter?.tradeListFiltered!![position]

                        pokemonCardsViewModel.selectedTrade = trade

                        mainActivityListener?.popBackStack()
                    }
                })
        )
    }
}

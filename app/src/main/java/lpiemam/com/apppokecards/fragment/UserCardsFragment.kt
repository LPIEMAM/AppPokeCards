package lpiemam.com.apppokecards.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_user_cards.*
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.listeners.RecyclerTouchListener
import lpiemam.com.apppokecards.adapter.UserCardsAdapter
import lpiemam.com.apppokecards.viewmodel.PokemonCardsViewModel
import java.util.*


/**
 * A simple [Fragment] subclass.
 *
 */
class UserCardsFragment : BaseFragment() {

    lateinit var pokemonCardsViewModel: PokemonCardsViewModel

    var userCardAdapter: UserCardsAdapter? = null

    lateinit var useCase: String

    companion object {
        fun newInstance(useCase: String): UserCardsFragment {
            val fragment = UserCardsFragment()
            fragment.useCase = useCase
            return fragment
        }
    }

    override fun onResume() {

        if (useCase == "trade") {
            setFragmentTitle("Pick A Card")
            setDrawerEnabled(false)
            setUpBackButton(true)
        } else {
            setFragmentTitle("My Pokemons")
            setUpBackButton(false)
            setDrawerEnabled(true)
        }

        userCardAdapter?.filter?.filter(collectionSearchView.query)
        super.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        if (useCase == "trade") {
            setFragmentTitle("Pick A Card")
            setDrawerEnabled(false)
            setUpBackButton(true)
        } else {
            setFragmentTitle("My Pokemons")
        }

        pokemonCardsViewModel = ViewModelProviders.of(activity!!).get(PokemonCardsViewModel::class.java)

        pokemonCardsViewModel.userCardListLiveData.observe(this, Observer {

            collectionProgressBar.visibility = View.GONE
            if(it != null && !it.isEmpty()) {
                it.sort()
                userCardAdapter?.setUpLists(it)
                pokemonCardsViewModel.userCardList = it
                userCardAdapter?.notifyDataSetChanged()
            } else {
                collectionNoCardTextView.visibility = View.VISIBLE
            }

        })

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_cards, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        collectionSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                userCardAdapter?.filter?.filter(s)
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                userCardAdapter?.filter?.filter(s)
                return false
            }
        })
    }


    private fun setUpRecyclerView() {
        userCardAdapter = UserCardsAdapter(ArrayList(pokemonCardsViewModel.userCardList))

        collectionRecyclerView?.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 4)
        collectionRecyclerView?.adapter = userCardAdapter

        collectionRecyclerView?.addOnItemTouchListener(
            RecyclerTouchListener(
                context!!,
                collectionRecyclerView!!,
                object : RecyclerTouchListener.ClickListener {
                    override fun onClick(view: View, position: Int) {
                        val userCard = userCardAdapter?.userCardsListFiltered!![position]
                        if (useCase == "trade") {

                            pokemonCardsViewModel.selectedUserCardForTrade = userCard

                            mainActivityListener?.popBackStack()
                        } else {
                            mainActivityListener?.replaceWithUserDetailFragment(userCard)
                        }

                    }

                    override fun onLongClick(view: View?, position: Int) {
                        val userCard = userCardAdapter?.userCardsListFiltered!![position]

                        if (useCase == "trade") {
                            pokemonCardsViewModel.selectedUserCardForTrade = userCard

                            mainActivityListener?.popBackStack()
                        } else {
                            mainActivityListener?.replaceWithUserDetailFragment(userCard)
                        }
                    }
                })
        )
    }
}

package lpiemam.com.apppokecards.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_pokemon_cards.*
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.RecyclerTouchListener
import lpiemam.com.apppokecards.adapter.PokemonCardsAdapter
import lpiemam.com.apppokecards.viewmodel.PokemonCardsViewModel


/**
 * A simple [Fragment] subclass.
 *
 */
class PokemonCardsFragment : BaseFragment() {

    lateinit var pokemonCardsViewModel: PokemonCardsViewModel

    companion object {

        fun newInstance(): PokemonCardsFragment {
            return PokemonCardsFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pokemonCardsViewModel = ViewModelProviders.of(activity!!).get(PokemonCardsViewModel::class.java)
        pokemonCardsViewModel.fetchPokemonCardsForName("")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setFragmentTitle("Pokedex")
        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pokemon_cards, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setUpRecyclerView()

        showActionBar(true)
        setUpBackButton(false)
        setDrawerEnabled(true)

        allCardsSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                pokemonCardsViewModel.fetchPokemonCardsForName(s)
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                pokemonCardsViewModel.fetchPokemonCardsForName(s)
                return false
            }
        })

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setUpRecyclerView() {
        val pokemonCardsAdapter = PokemonCardsAdapter()
        pokemonCardsViewModel.pokemonCardsForNameLiveData.observe(this, Observer {
            pokemonCardsAdapter.setData(it)
        })

        allCardsRecyclerView?.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 4)
        allCardsRecyclerView?.adapter = pokemonCardsAdapter

        allCardsRecyclerView?.addOnItemTouchListener(
            RecyclerTouchListener(
                context!!,
                allCardsRecyclerView!!,
                object : RecyclerTouchListener.ClickListener {
                    override fun onClick(view: View, position: Int) {

                        val card = pokemonCardsAdapter.allPokemonCardsList[position]

                        mainActivityListener?.replaceWithAllCardsDetailFragment(card)
                    }

                    override fun onLongClick(view: View?, position: Int) {
                        val card = pokemonCardsAdapter.allPokemonCardsList[position]

                        mainActivityListener?.replaceWithAllCardsDetailFragment(card)
                    }
                })
        )
    }

}

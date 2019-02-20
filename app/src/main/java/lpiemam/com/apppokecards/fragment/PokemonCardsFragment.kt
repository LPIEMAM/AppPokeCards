package lpiemam.com.apppokecards.fragment


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_pokemon_cards.*
import lpiemam.com.apppokecards.*
import lpiemam.com.apppokecards.adapter.PokemonCardsAdapter
import lpiemam.com.apppokecards.viewmodel.PokemonCardsViewModel



/**
 * A simple [Fragment] subclass.
 *
 */
class PokemonCardsFragment : Fragment() {

    lateinit var pokemonCardsViewModel: PokemonCardsViewModel

    var pokemonCardsAdapter: PokemonCardsAdapter? = null
    var replaceFragmentListener: ReplaceFragmentListener? = null

    companion object {

        fun newInstance(): PokemonCardsFragment {
            return PokemonCardsFragment()
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        replaceFragmentListener = context as? ReplaceFragmentListener
        if (replaceFragmentListener == null) {
            throw ClassCastException("$context must implement OnCardSelectedListener")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        replaceFragmentListener = null
        super.onDetach()
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

        setHasOptionsMenu(true)



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pokemon_cards, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {





        setUpRecyclerView()
        replaceFragmentListener!!.setUpBackButton(false)
        replaceFragmentListener!!.setDrawerEnabled(true)

        (context as MainActivity).supportActionBar!!.show()
        allCardsSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                Log.d("", "onQueryTextChange: $s")
                pokemonCardsViewModel.fetchPokemonCardsForName(s)
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                //CharSequence charSequence = searchView.getQuery();
                Log.d("", "onQueryTextChange: $s")
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

        allCardsRecyclerView!!.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 4)
        allCardsRecyclerView!!.adapter = pokemonCardsAdapter

        allCardsRecyclerView!!.addOnItemTouchListener(
            RecyclerTouchListener(
                context!!,
                allCardsRecyclerView!!,
                object : RecyclerTouchListener.ClickListener {
                    override fun onClick(view: View, position: Int) {

                        val card = pokemonCardsAdapter!!.allPokemonCardsList[position]

                        replaceFragmentListener!!.replaceWithAllCardsDetailFragment(card)
                    }

                    override fun onLongClick(view: View?, position: Int) {
                        val card = pokemonCardsAdapter!!.allPokemonCardsList[position]

                        replaceFragmentListener!!.replaceWithAllCardsDetailFragment(card)
                    }
                })
        )

    }

}

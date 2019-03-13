package lpiemam.com.apppokecards.fragment


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_user_cards.*
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.RecyclerTouchListener
import lpiemam.com.apppokecards.MainActivityListener
import lpiemam.com.apppokecards.adapter.UserCardsAdapter
import lpiemam.com.apppokecards.viewmodel.PokemonCardsViewModel



/**
 * A simple [Fragment] subclass.
 *
 */
class UserCardsFragment : androidx.fragment.app.Fragment() {

    lateinit var pokemonCardsViewModel: PokemonCardsViewModel

    var userCardAdapter: UserCardsAdapter? = null
    var mainActivityListener: MainActivityListener? = null


    companion object {
        fun newInstance(): UserCardsFragment {
            return UserCardsFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivityListener = context as? MainActivityListener
        if (mainActivityListener == null) {
            throw ClassCastException("$context must implement OnCardSelectedListener")
        }
    }

    override fun onDetach() {

        mainActivityListener = null
        super.onDetach()
    }

    override fun onResume() {

        mainActivityListener!!.setUpBackButton(false)
        mainActivityListener!!.setDrawerEnabled(true)
        super.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mainActivityListener!!.setFragmentTitle("Collection")

        setHasOptionsMenu(true)


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_cards, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        pokemonCardsViewModel = ViewModelProviders.of(activity!!).get(PokemonCardsViewModel::class.java)

        setUpRecyclerView()

        /*floatingActionButtonAddPokemon.setOnClickListener {
            mainActivityListener!!.replaceWithAddNewCardFragment()
        }*/

        collectionSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                Log.d("", "onQueryTextChange: $s")
                userCardAdapter!!.filter!!.filter(s)
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                //CharSequence charSequence = searchView.getQuery();
                Log.d("", "onQueryTextChange: $s")
                userCardAdapter!!.filter!!.filter(s)
                return false
            }
        })

        super.onViewCreated(view, savedInstanceState)
    }


    private fun setUpRecyclerView() {
        userCardAdapter = UserCardsAdapter(ArrayList(pokemonCardsViewModel.userCardList))

        collectionRecyclerView!!.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 4)
        collectionRecyclerView!!.adapter = userCardAdapter

        collectionRecyclerView!!.addOnItemTouchListener(
            RecyclerTouchListener(
                context!!,
                collectionRecyclerView!!,
                object : RecyclerTouchListener.ClickListener {
                    override fun onClick(view: View, position: Int) {

                        val userCard = userCardAdapter!!.userCardList[position]

                        mainActivityListener!!.replaceWithUserDetailFragment(userCard)
                    }

                    override fun onLongClick(view: View?, position: Int) {
                        val userCard = userCardAdapter!!.userCardList[position]

                        mainActivityListener!!.replaceWithUserDetailFragment(userCard)
                    }
                })
        )
    }
}

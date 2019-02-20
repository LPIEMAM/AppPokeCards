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
import kotlinx.android.synthetic.main.fragment_collection.*
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.RecyclerTouchListener
import lpiemam.com.apppokecards.ReplaceFragmentListener
import lpiemam.com.apppokecards.adapter.UserCardsAdapter
import lpiemam.com.apppokecards.viewmodel.ViewModelPokemon



/**
 * A simple [Fragment] subclass.
 *
 */
class CollectionFragment : androidx.fragment.app.Fragment() {

    lateinit var viewModelPokemon: ViewModelPokemon

    var userCardAdapter: UserCardsAdapter? = null
    var replaceFragmentListener: ReplaceFragmentListener? = null


    companion object {
        fun newInstance(): CollectionFragment {
            return CollectionFragment()
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
        super.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        setHasOptionsMenu(true)


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collection, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModelPokemon = ViewModelProviders.of(activity!!).get(ViewModelPokemon::class.java)

        setUpRecyclerView()

        /*floatingActionButtonAddPokemon.setOnClickListener {
            replaceFragmentListener!!.replaceWithAddNewCardFragment()
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
        userCardAdapter = UserCardsAdapter(ArrayList(viewModelPokemon.userCardList))

        collectionRecyclerView!!.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 4)
        collectionRecyclerView!!.adapter = userCardAdapter

        collectionRecyclerView!!.addOnItemTouchListener(
            RecyclerTouchListener(
                context!!,
                collectionRecyclerView!!,
                object : RecyclerTouchListener.ClickListener {
                    override fun onClick(view: View, position: Int) {

                        val userCard = userCardAdapter!!.userCardList[position]

                        replaceFragmentListener!!.replaceWithUserDetailFragment(userCard)
                    }

                    override fun onLongClick(view: View?, position: Int) {
                        val userCard = userCardAdapter!!.userCardList[position]

                        replaceFragmentListener!!.replaceWithUserDetailFragment(userCard)
                    }
                })
        )
    }
}

package lpiemam.com.apppokecards.fragment


import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_collection.*
import lpiemam.com.apppokecards.MainActivity
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.RecyclerTouchListener
import lpiemam.com.apppokecards.ReplaceFragmentListener
import lpiemam.com.apppokecards.adapter.UserCardsAdapter
import lpiemam.com.apppokecards.model.Card


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class CollectionFragment : Fragment() {

    private lateinit var mainActivity : MainActivity
    private lateinit var collectionFragment: CollectionFragment
    private var recyclerView: RecyclerView? = null
    var userCardsAdapter: UserCardsAdapter? = null
    private lateinit var userCardDetailFragment: UserCardDetailFragment

    var replaceFragmentListener: ReplaceFragmentListener? = null
    private lateinit var addNewCardFragment : AddNewCardFragment


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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mainActivity = context as MainActivity

        setHasOptionsMenu(true)


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collection, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        setUpRecyclerView()

        floatingActionButtonAddPokemon.setOnClickListener { view ->
            fragmentManager!!
                .beginTransaction()
                .add(R.id.mainActivityContainer, mainActivity.addNewCardFragment, "addNewCardFragment").addToBackStack("addNewCardFragment")
                .commit()
        }

        collectionSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                Log.d("", "onQueryTextChange: $s")
                userCardsAdapter!!.filter!!.filter(s)
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                //CharSequence charSequence = searchView.getQuery();
                Log.d("", "onQueryTextChange: $s")
                userCardsAdapter!!.filter!!.filter(s)
                return false
            }
        })




        super.onViewCreated(view, savedInstanceState)
    }


    private fun setUpRecyclerView() {
        userCardsAdapter = UserCardsAdapter(ArrayList(mainActivity.userSiam.userCardList))

        collectionRecyclerView!!.layoutManager = GridLayoutManager(context, 4)
        collectionRecyclerView!!.adapter = userCardsAdapter

        collectionRecyclerView!!.addOnItemTouchListener(
            RecyclerTouchListener(
                context!!,
                collectionRecyclerView!!,
                object : RecyclerTouchListener.ClickListener {
                    override fun onClick(view: View, position: Int) {

                        val card = userCardsAdapter!!.cardList[position]

                        replaceFragmentListener!!.showUserCardDetail(card)
                    }

                    override fun onLongClick(view: View?, position: Int) {
                        val card = userCardsAdapter!!.cardList[position]

                        replaceFragmentListener!!.showUserCardDetail(card)
                    }
                })
        )
    }
}

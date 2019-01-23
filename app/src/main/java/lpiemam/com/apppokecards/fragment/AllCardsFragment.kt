package lpiemam.com.apppokecards.fragment


import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_collection.*
import lpiemam.com.apppokecards.*
import lpiemam.com.apppokecards.adapter.AllCardsAdapter


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class AllCardsFragment : Fragment() {

    private lateinit var mainActivity : MainActivity
    private var allCardsAdapter: AllCardsAdapter? = null
    var replaceFragmentListener: ReplaceFragmentListener? = null

    companion object {

        fun newInstance(): AllCardsFragment {
            return AllCardsFragment()
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

        val view = inflater.inflate(R.layout.fragment_collection, container, false)

        mainActivity = (context as MainActivity?)!!
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        floatingActionButtonAddPokemon.setOnClickListener { view ->
            Snackbar.make(view, "Add a new Card", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        collectionSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                Log.d("", "onQueryTextChange: $s")
                allCardsAdapter!!.filter!!.filter(s)
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                //CharSequence charSequence = searchView.getQuery();
                Log.d("", "onQueryTextChange: $s")
                allCardsAdapter!!.filter!!.filter(s)
                return false
            }
        })

        setUpRecyclerView()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setUpRecyclerView() {
        allCardsAdapter = AllCardsAdapter(mainActivity.allCardsList)

        collectionRecyclerView!!.layoutManager = GridLayoutManager(context, 4)
        collectionRecyclerView!!.adapter = allCardsAdapter

        collectionRecyclerView!!.addOnItemTouchListener(
            RecyclerTouchListener(
                context!!,
                collectionRecyclerView!!,
                object : RecyclerTouchListener.ClickListener {
                    override fun onClick(view: View, position: Int) {
                        /*val userCard = userCardsAdapter!!.userCardsList.get(position)
                        (plantDetailsFragment as PlantDetailsFragment).setUserPlant(userPlant)
                        mainActivity.replaceFragment(plantDetailsFragment)*/
                    }

                    override fun onLongClick(view: View?, position: Int) {
                        /*val userPlant = userPlantViewAdapter.getUserPlantList().get(position)
                        (plantDetailsFragment as PlantDetailsFragment).setUserPlant(userPlant)
                        mainActivity.replaceFragment(plantDetailsFragment)*/
                    }
                })
        )
    }

}

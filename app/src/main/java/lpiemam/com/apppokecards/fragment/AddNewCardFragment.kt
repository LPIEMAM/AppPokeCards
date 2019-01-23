package lpiemam.com.apppokecards.fragment


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_add_new_card.*
import kotlinx.android.synthetic.main.fragment_collection.*
import lpiemam.com.apppokecards.MainActivity
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.RecyclerTouchListener
import lpiemam.com.apppokecards.ReplaceFragmentListener
import lpiemam.com.apppokecards.adapter.AddNewCardAdapter


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class AddNewCardFragment : Fragment() {

    private lateinit var mainActivity : MainActivity
    private var addNewCardAdapter: AddNewCardAdapter? = null
    var replaceFragmentListener: ReplaceFragmentListener? = null


    companion object {

        fun newInstance(): AddNewCardFragment {
            return AddNewCardFragment()
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

        replaceFragmentListener!!.setUpBackButton(false)
        replaceFragmentListener!!.setDrawerEnabled(true)

        replaceFragmentListener = null
        super.onDetach()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_add_new_card, container, false)

        mainActivity = (context as MainActivity?)!!
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        replaceFragmentListener!!.setDrawerEnabled(false)
        replaceFragmentListener!!.setUpBackButton(true)
        addNewCardSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(s: String): Boolean {
                Log.d("", "onQueryTextChange: $s")
                addNewCardAdapter!!.filter!!.filter(s)
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                //CharSequence charSequence = searchView.getQuery();
                Log.d("", "onQueryTextChange: $s")
                addNewCardAdapter!!.filter!!.filter(s)
                return false
            }
        })

        setUpRecyclerView()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setUpRecyclerView() {
        addNewCardAdapter = AddNewCardAdapter(ArrayList(mainActivity.allCardsUserNeeds))

        addNewCardRecyclerView!!.layoutManager = GridLayoutManager(context, 4)
        addNewCardRecyclerView!!.adapter = addNewCardAdapter

        addNewCardRecyclerView!!.addOnItemTouchListener(
                RecyclerTouchListener(
                        context!!,
                    addNewCardRecyclerView!!,
                        object : RecyclerTouchListener.ClickListener {
                            override fun onClick(view: View, position: Int) {
                                val userCard = addNewCardAdapter!!.allCardsUserNeeds.get(position)
                                mainActivity.userSiam.userCardList.add(userCard)
                                mainActivity.allCardsUserNeeds.remove(userCard)
                                mainActivity.userSiam.userCardList = ArrayList(mainActivity.userSiam.userCardList.sortedWith(compareBy{it.pokemon.pokedexNumber}))
                                mainActivity.collectionFragment.userCardsAdapter!!.notifyDataSetChanged()
                                mainActivity.supportFragmentManager
                                    .beginTransaction()
                                    .replace(R.id.mainActivityContainer, CollectionFragment.newInstance(), "collectionFragment")
                                    .commit()
                            }

                            override fun onLongClick(view: View?, position: Int) {
                                val userCard = addNewCardAdapter!!.allCardsUserNeeds.get(position)
                                mainActivity.userSiam.userCardList.add(userCard)
                                mainActivity.allCardsUserNeeds.remove(userCard)
                                mainActivity.userSiam.userCardList = ArrayList(mainActivity.userSiam.userCardList.sortedWith(compareBy{it.pokemon.pokedexNumber}))
                                mainActivity.collectionFragment.userCardsAdapter!!.notifyDataSetChanged()
                                mainActivity.supportFragmentManager
                                    .beginTransaction()
                                    .replace(R.id.mainActivityContainer, CollectionFragment.newInstance(), "collectionFragment")
                                    .commit()
                            }
                        })
        )
    }

}

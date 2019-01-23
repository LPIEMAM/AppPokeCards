package lpiemam.com.apppokecards.fragment


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_add_new_card.*
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.RecyclerTouchListener
import lpiemam.com.apppokecards.ReplaceFragmentListener
import lpiemam.com.apppokecards.adapter.AddNewCardAdapter
import lpiemam.com.apppokecards.model.Manager


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class AddNewCardFragment : androidx.fragment.app.Fragment() {

    var addNewCardAdapter: AddNewCardAdapter? = null
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
        addNewCardAdapter = AddNewCardAdapter(ArrayList(Manager.allCardsUserNeeds))

        addNewCardRecyclerView!!.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 4)
        addNewCardRecyclerView!!.adapter = addNewCardAdapter

        addNewCardRecyclerView!!.addOnItemTouchListener(
                RecyclerTouchListener(
                        context!!,
                    addNewCardRecyclerView!!,
                        object : RecyclerTouchListener.ClickListener {
                            override fun onClick(view: View, position: Int) {
                                val userCard = addNewCardAdapter!!.allCardsUserNeeds.get(position)
                                Manager.userSiam.userCardList.add(userCard)
                                Manager.allCardsUserNeeds.remove(userCard)
                                Manager.userSiam.userCardList = ArrayList(Manager.userSiam.userCardList.sortedWith(compareBy{it.pokemon.pokedexNumber}))
                                replaceFragmentListener!!.notifyCollectionDataSetChanged()
                                replaceFragmentListener!!.popBackStack()
//                                replaceFragmentListener!!.replaceWithFragment(collectionFragment!!, null)
                            }

                            override fun onLongClick(view: View?, position: Int) {
                                val userCard = addNewCardAdapter!!.allCardsUserNeeds.get(position)
                                Manager.userSiam.userCardList.add(userCard)
                                Manager.allCardsUserNeeds.remove(userCard)
                                Manager.userSiam.userCardList = ArrayList(Manager.userSiam.userCardList.sortedWith(compareBy{it.pokemon.pokedexNumber}))
                                replaceFragmentListener!!.notifyCollectionDataSetChanged()
                                replaceFragmentListener!!.popBackStack()
                            }
                        })
        )
    }

}

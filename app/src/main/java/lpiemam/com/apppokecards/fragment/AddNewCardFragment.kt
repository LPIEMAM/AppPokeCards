package lpiemam.com.apppokecards.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_collection.*
import lpiemam.com.apppokecards.MainActivity
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.RecyclerTouchListener
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

    companion object {

        fun newInstance(): AddNewCardFragment {
            return AddNewCardFragment()
        }
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
        collectionSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
        addNewCardAdapter = AddNewCardAdapter(mainActivity.allCardsUserNeeds)

        collectionRecyclerView!!.layoutManager = GridLayoutManager(context, 4)
        collectionRecyclerView!!.adapter = addNewCardAdapter

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

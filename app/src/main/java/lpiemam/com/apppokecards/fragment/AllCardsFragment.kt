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
import kotlinx.android.synthetic.main.fragment_all_cards.*
import lpiemam.com.apppokecards.*
import lpiemam.com.apppokecards.adapter.AllCardAdapter
import lpiemam.com.apppokecards.viewmodel.ViewModelPokemon



/**
 * A simple [Fragment] subclass.
 *
 */
class AllCardsFragment : Fragment() {

    lateinit var viewModelPokemon: ViewModelPokemon

    var allCardAdapter: AllCardAdapter? = null
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

        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_cards, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        viewModelPokemon = ViewModelProviders.of(activity!!).get(ViewModelPokemon::class.java)

        setUpRecyclerView()
        replaceFragmentListener!!.setUpBackButton(false)
        replaceFragmentListener!!.setDrawerEnabled(true)

        (context as MainActivity).supportActionBar!!.show()
        allCardsSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                Log.d("", "onQueryTextChange: $s")
                allCardAdapter!!.filter!!.filter(s)
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                //CharSequence charSequence = searchView.getQuery();
                Log.d("", "onQueryTextChange: $s")
                allCardAdapter!!.filter!!.filter(s)
                return false
            }
        })

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setUpRecyclerView() {
        allCardAdapter = AllCardAdapter(ArrayList(viewModelPokemon.allCardsList))

        allCardsRecyclerView!!.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 4)
        allCardsRecyclerView!!.adapter = allCardAdapter

        allCardsRecyclerView!!.addOnItemTouchListener(
            RecyclerTouchListener(
                context!!,
                allCardsRecyclerView!!,
                object : RecyclerTouchListener.ClickListener {
                    override fun onClick(view: View, position: Int) {

                        val card = allCardAdapter!!.allCardList[position]

                        replaceFragmentListener!!.replaceWithAllCardsDetailFragment(card)
                    }

                    override fun onLongClick(view: View?, position: Int) {
                        val card = allCardAdapter!!.allCardList[position]

                        replaceFragmentListener!!.replaceWithAllCardsDetailFragment(card)
                    }
                })
        )
    }

}

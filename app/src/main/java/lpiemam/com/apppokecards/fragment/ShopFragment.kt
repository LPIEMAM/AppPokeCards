package lpiemam.com.apppokecards.fragment


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import kotlinx.android.synthetic.main.fragment_collection.*

import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.RecyclerTouchListener
import lpiemam.com.apppokecards.ReplaceFragmentListener
import lpiemam.com.apppokecards.adapter.ShopAdapter
import lpiemam.com.apppokecards.adapter.UserCardsAdapter
import lpiemam.com.apppokecards.model.Manager


/**
 * A simple [Fragment] subclass.
 */
class ShopFragment : androidx.fragment.app.Fragment() {

    var shopAdapter : ShopAdapter? = null
    var replaceFragmentListener: ReplaceFragmentListener? = null

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
        setUpRecyclerView()



        super.onViewCreated(view, savedInstanceState)
    }


    private fun setUpRecyclerView() {
        shopAdapter = ShopAdapter(ArrayList(Manager.cardsPacksList))

        collectionRecyclerView!!.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 4)
        collectionRecyclerView!!.adapter = userCardAdapter

        collectionRecyclerView!!.addOnItemTouchListener(
                RecyclerTouchListener(
                        context!!,
                        collectionRecyclerView!!,
                        object : RecyclerTouchListener.ClickListener {
                            override fun onClick(view: View, position: Int) {

                                val card = userCardAdapter!!.cardList[position]

                                replaceFragmentListener!!.replaceWithUserDetailFragment(card)
                            }

                            override fun onLongClick(view: View?, position: Int) {
                                val card = userCardAdapter!!.cardList[position]

                                replaceFragmentListener!!.replaceWithUserDetailFragment(card)
                            }
                        })
        )
    }
    }

}
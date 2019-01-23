package lpiemam.com.apppokecards.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.viewholder.UserCardsViewHolder
import android.widget.Filterable
import lpiemam.com.apppokecards.model.Pokemon

class UserCardsAdapter(val pokemonList: ArrayList<Pokemon>) : RecyclerView.Adapter<UserCardsViewHolder>(), Filterable {

    private var userCardsListFiltered: ArrayList<Pokemon>? = pokemonList


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): UserCardsViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.rv_row_collection, viewGroup, false)
        return UserCardsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userCardsListFiltered!!.size
    }

    override fun onBindViewHolder(userCardsViewHolder: UserCardsViewHolder, i: Int) {
        val pokemon = userCardsListFiltered!![i]
        userCardsViewHolder.bind(pokemon)
    }

    override fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    userCardsListFiltered?.clear()
                    userCardsListFiltered?.addAll(pokemonList)
                } else {
                    val filteredList = java.util.ArrayList<Pokemon>()
                    for (card in pokemonList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (card.name.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(card)
                        }
                    }

                    userCardsListFiltered = filteredList
                }

                val filterResults = Filter.FilterResults()
                filterResults.values = userCardsListFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                userCardsListFiltered = filterResults.values as java.util.ArrayList<Pokemon>

                // refresh the replaceFragmentListener with filtered data
                this@UserCardsAdapter.notifyDataSetChanged()
            }
        }
    }

}
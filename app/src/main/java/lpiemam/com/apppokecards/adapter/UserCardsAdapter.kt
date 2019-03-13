package lpiemam.com.apppokecards.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.viewholder.UserCardViewHolder
import android.widget.Filterable
import lpiemam.com.apppokecards.model.UserCard

class UserCardsAdapter(val userCardList: ArrayList<UserCard>) : androidx.recyclerview.widget.RecyclerView.Adapter<UserCardViewHolder>(), Filterable {

    private var userCardsListFiltered: ArrayList<UserCard>? = ArrayList(userCardList)


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): UserCardViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.rv_row_collection, viewGroup, false)
        return UserCardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userCardsListFiltered!!.size
    }

    override fun onBindViewHolder(userCardViewHolder: UserCardViewHolder, i: Int) {
        val card = userCardsListFiltered!![i]
        userCardViewHolder.bind(card)
    }

    override fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    userCardsListFiltered?.clear()
                    userCardsListFiltered?.addAll(userCardList)
                } else {
                    val filteredList = java.util.ArrayList<UserCard>()
                    for (userCard in userCardList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (userCard.pokemonCard.name.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(userCard)
                        }
                    }

                    userCardsListFiltered = filteredList
                }

                val filterResults = Filter.FilterResults()
                filterResults.values = userCardsListFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                userCardsListFiltered = filterResults.values as java.util.ArrayList<UserCard>

                // refresh the mainActivityListener with filtered data
                notifyDataSetChanged()
            }
        }
    }

}
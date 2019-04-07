package lpiemam.com.apppokecards.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.model.Trade
import lpiemam.com.apppokecards.viewholder.UserCardViewHolder
import java.util.*

class TradeAdapter(val tradeList: ArrayList<Trade>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<UserCardViewHolder>(), Filterable {
    var tradeListFiltered: ArrayList<Trade> = ArrayList(tradeList)

    fun setUpLists(tradeList: ArrayList<Trade>) {
        this.tradeList.clear()
        this.tradeList.addAll(tradeList)
        this.tradeListFiltered.clear()
        this.tradeListFiltered.addAll(this.tradeList)
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): UserCardViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.rv_row_collection, viewGroup, false)
        return UserCardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tradeListFiltered.size
    }

    override fun onBindViewHolder(userCardViewHolder: UserCardViewHolder, i: Int) {
        val trade = tradeListFiltered[i]
        userCardViewHolder.bind(trade.userCard1!!)
    }

    override fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    tradeListFiltered.clear()
                    tradeListFiltered.addAll(tradeList)
                } else {
                    val filteredList = java.util.ArrayList<Trade>()
                    for (trade in tradeList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (trade.userCard1!!.pokemonCard.name.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(trade)
                        }
                    }

                    tradeListFiltered = filteredList
                }

                val filterResults = Filter.FilterResults()
                filterResults.values = tradeListFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                tradeListFiltered = filterResults.values as java.util.ArrayList<Trade>
                tradeListFiltered.sort()

                // refresh the mainActivityListener with filtered data
                notifyDataSetChanged()
            }
        }
    }
}
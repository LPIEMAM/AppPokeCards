package lpiemam.com.apppokecards.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.model.Card
import lpiemam.com.apppokecards.viewholder.AllCardsViewHolder


class AllCardAdapter(val allCardList: ArrayList<Card>) : androidx.recyclerview.widget.RecyclerView.Adapter<AllCardsViewHolder>(),
    Filterable {

    private var allCardsListFiltered: ArrayList<Card> = ArrayList(allCardList)


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): AllCardsViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.rv_row_allcards, viewGroup, false)
        return AllCardsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return allCardsListFiltered!!.size
    }

    override fun onBindViewHolder(allCardsViewHolder: AllCardsViewHolder, i: Int) {
        val card = allCardsListFiltered!![i]
        allCardsViewHolder.bind(card)
    }

    override fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    allCardsListFiltered?.clear()
                    allCardsListFiltered?.addAll(allCardList)
                } else {
                    val filteredList = java.util.ArrayList<Card>()
                    for (card in allCardList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (card.pokemon.name.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(card)
                        }
                    }

                    allCardsListFiltered = filteredList
                }

                val filterResults = Filter.FilterResults()
                filterResults.values = allCardsListFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                allCardsListFiltered = filterResults.values as java.util.ArrayList<Card>

                // refresh the list with filtered data
                this@AllCardAdapter.notifyDataSetChanged()
            }
        }
    }

}
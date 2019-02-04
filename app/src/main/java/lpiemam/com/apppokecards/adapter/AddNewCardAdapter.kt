package lpiemam.com.apppokecards.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.model.Card
import lpiemam.com.apppokecards.viewholder.AddNewCardViewHolder
import lpiemam.com.apppokecards.viewholder.AllCardsViewHolder


class AddNewCardAdapter(val allCardsUserNeeds: ArrayList<Card>) : androidx.recyclerview.widget.RecyclerView.Adapter<AddNewCardViewHolder>(),
    Filterable {

    private var allCardsUserNeedsFiltered: ArrayList<Card> = ArrayList(allCardsUserNeeds)


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): AddNewCardViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.rv_row_allcards, viewGroup, false)
        return AddNewCardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return allCardsUserNeedsFiltered.size
    }

    override fun onBindViewHolder(addNewCardViewHolder: AddNewCardViewHolder, i: Int) {
        val card = allCardsUserNeedsFiltered[i]
        addNewCardViewHolder.bind(card)
    }

    override fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    allCardsUserNeedsFiltered.clear()
                    allCardsUserNeedsFiltered.addAll(allCardsUserNeeds)
                } else {
                    val filteredList = java.util.ArrayList<Card>()
                    for (card in allCardsUserNeeds) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (card.pokemon.name.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(card)
                        }
                    }

                    allCardsUserNeedsFiltered = filteredList
                }

                val filterResults = Filter.FilterResults()
                filterResults.values = allCardsUserNeedsFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                allCardsUserNeedsFiltered = filterResults.values as java.util.ArrayList<Card>

                // refresh the list with filtered data
                this@AddNewCardAdapter.notifyDataSetChanged()
            }
        }
    }
}
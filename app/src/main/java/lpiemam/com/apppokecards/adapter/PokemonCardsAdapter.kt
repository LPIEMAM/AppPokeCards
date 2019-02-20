package lpiemam.com.apppokecards.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.model.PokemonCard
import lpiemam.com.apppokecards.viewholder.PokemonCardViewHolder


class PokemonCardsAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<PokemonCardViewHolder>() {

    var allPokemonCardsList = ArrayList<PokemonCard>()

    fun setData(listCard: ArrayList<PokemonCard>) {
        this.allPokemonCardsList = listCard
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): PokemonCardViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.rv_row_allcards, viewGroup, false)
        return PokemonCardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return allPokemonCardsList!!.size
    }

    override fun onBindViewHolder(pokemonCardViewHolder: PokemonCardViewHolder, i: Int) {
        val card = allPokemonCardsList!![i]
        pokemonCardViewHolder.bind(card)
    }

//    override fun getFilter(): Filter? {
//        return object : Filter() {
//            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
//                val charString = charSequence.toString()
//                if (charString.isEmpty()) {
//                    allCardsListFiltered?.clear()
//                    allCardsListFiltered?.addAll(allPokemonCardList)
//                } else {
//                    val filteredList = java.util.ArrayList<PokemonCard>()
//                    for (card in allPokemonCardList) {
//
//                        // name match condition. this might differ depending on your requirement
//                        // here we are looking for name or phone number match
//                        if (card.name.toLowerCase().contains(charString.toLowerCase())) {
//                            filteredList.add(card)
//                        }
//                    }
//
//                    allCardsListFiltered = filteredList
//                }
//
//                val filterResults = Filter.FilterResults()
//                filterResults.values = allCardsListFiltered
//                return filterResults
//            }
//
//            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
//                allCardsListFiltered = filterResults.values as java.util.ArrayList<PokemonCard>
//
//                // refresh the list with filtered data
//                this@PokemonCardsAdapter.notifyDataSetChanged()
//            }
//        }
//    }

}
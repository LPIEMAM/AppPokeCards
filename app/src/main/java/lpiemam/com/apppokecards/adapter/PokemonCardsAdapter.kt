package lpiemam.com.apppokecards.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import lpiemam.com.apppokecards.OnBottomReachedListener
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.model.PokemonCard
import lpiemam.com.apppokecards.viewholder.PokemonCardViewHolder


class PokemonCardsAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<PokemonCardViewHolder>() {

    var allPokemonCardsList = ArrayList<PokemonCard>()

    var onBottomReachedListener: OnBottomReachedListener? = null

    fun setData(listCard: ArrayList<PokemonCard>) {
        this.allPokemonCardsList = listCard
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): PokemonCardViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.rv_row_allcards, viewGroup, false)
        return PokemonCardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return allPokemonCardsList.size
    }

    override fun onBindViewHolder(pokemonCardViewHolder: PokemonCardViewHolder, i: Int) {
        val card = allPokemonCardsList[i]
        pokemonCardViewHolder.bind(card)
        if (i == allPokemonCardsList.size - 3){

            onBottomReachedListener?.onBottomReached(i)

        }
    }

}
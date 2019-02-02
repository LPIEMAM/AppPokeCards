package lpiemam.com.apppokecards.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import lpiemam.com.apppokecards.model.Card
import lpiemam.com.apppokecards.model.CardsPack
import lpiemam.com.apppokecards.viewholder.CardsPackViewHolder
import lpiemam.com.apppokecards.viewholder.UserCardsViewHolder

/**
 * Created by lpiem on 02/02/2019.
 */
class ShopAdapter (val cardsPackList: ArrayList<CardsPack>) : androidx.recyclerview.widget.RecyclerView.Adapter<CardsPackViewHolder>() {

    private var allCardPackList: ArrayList<CardsPack>? = ArrayList(cardsPackList)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CardsPackViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.rv_row_collection, viewGroup, false)
        return CardsPackViewHolder(view)
    }

    override fun getItemCount(): Int {
        return allCardPackList!!.size
    }

    override fun onBindViewHolder(cardsPackViewHolder: CardsPackViewHolder, i: Int) {
        val cardsPack = allCardPackList!![i]
        cardsPackViewHolder.bind(cardsPack)
    }

}
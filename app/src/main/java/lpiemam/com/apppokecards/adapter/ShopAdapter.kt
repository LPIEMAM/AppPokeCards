package lpiemam.com.apppokecards.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.model.CardsPack
import lpiemam.com.apppokecards.viewholder.CardsPackViewHolder

/**
 * Created by lpiem on 02/02/2019.
 */
class ShopAdapter(val cardsPackList: ArrayList<CardsPack>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<CardsPackViewHolder>() {

    private var allCardPackList: ArrayList<CardsPack>? = ArrayList(cardsPackList)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CardsPackViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.rv_row_cardspack, viewGroup, false)
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
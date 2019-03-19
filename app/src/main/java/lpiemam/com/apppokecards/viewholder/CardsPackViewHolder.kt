package lpiemam.com.apppokecards.viewholder

import android.view.View
import kotlinx.android.synthetic.main.rv_row_cardspack.view.*
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.model.CardsPack

/**
 * Created by lpiem on 03/02/2019.
 */
class CardsPackViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    fun bind(cardPack: CardsPack) {
        itemView.packTypeTextView.text = cardPack.name
        itemView.packPriceTextView.text = cardPack.costPack.toString()
        itemView.packCardsNumberTextView.text = cardPack.nbCards.toString()
        if (cardPack.isSelected) {
            itemView.setBackgroundResource(R.drawable.pack_layout_border)
        } else {
            itemView.background = null
        }
    }
}
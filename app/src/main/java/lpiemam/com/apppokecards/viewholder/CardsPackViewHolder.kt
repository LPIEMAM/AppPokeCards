package lpiemam.com.apppokecards.viewholder

import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rv_row_cardspack.view.*
import lpiemam.com.apppokecards.model.Card
import lpiemam.com.apppokecards.model.CardsPack

/**
 * Created by lpiem on 03/02/2019.
 */
class CardsPackViewHolder (itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    private var ivCard  : ImageView = itemView.findViewById(R.id.cardsPackImageViewCard)

    fun bind(cardPack: CardsPack) {
        Picasso.get().load(cardPack).placeholder(R.drawable.pokemon_card_back).into(ivCard)
    }
}
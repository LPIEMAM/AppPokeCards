package lpiemam.com.apppokecards.viewholder

import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.model.Card

class AllCardsViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    private var ivCard  : ImageView = itemView.findViewById(R.id.allCardsImageViewCard)

    fun bind(card: Card) {
        Picasso.get().load(card.url).placeholder(R.drawable.pokemon_card_back).into(ivCard)
    }
}
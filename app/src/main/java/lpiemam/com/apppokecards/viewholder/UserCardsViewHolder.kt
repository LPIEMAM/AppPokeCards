package lpiemam.com.apppokecards.viewholder

import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.model.Card

class UserCardsViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    private var ivCard  : ImageView = itemView.findViewById(R.id.collectionImageViewCard)

    fun bind(card: Card) {
        Picasso.get().load(card.url).placeholder(R.drawable.pokemon_card_back).into(ivCard)
    }
}
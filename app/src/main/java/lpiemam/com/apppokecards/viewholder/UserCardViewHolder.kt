package lpiemam.com.apppokecards.viewholder

import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rv_row_collection.view.*
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.model.UserCard

class UserCardViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    private var ivCard: ImageView = itemView.findViewById(R.id.collectionImageViewCard)

    fun bind(userCard: UserCard) {
        Picasso.get().load(userCard.pokemonCard.imageUrlHiRes).placeholder(R.drawable.pokemon_card_back).into(ivCard)
        if (userCard.numberOfCard > 1) {
            itemView.numberCardsTextView.text = userCard.numberOfCard.toString()
            itemView.numberCardsTextView.visibility = View.VISIBLE
        } else {
            itemView.numberCardsTextView.visibility = View.GONE
        }
    }
}
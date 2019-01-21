package lpiemam.com.apppokecards.viewholder

import android.media.Image
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.model.Card
import lpiemam.com.apppokecards.model.GlideApp
import lpiemam.com.apppokecards.model.MyGlideAppModule

class UserCardsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var ivCard  : ImageView = itemView.findViewById(R.id.collectionImageViewCard)

    fun bind(card: Card) {
        GlideApp.with(itemView).load(card.url).placeholder(R.drawable.pokemon_card_back).into(ivCard)
    }
}
package lpiemam.com.apppokecards.viewholder

import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.R.id.allCardsImageViewCard
import lpiemam.com.apppokecards.model.Card
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.rv_row_allcards.view.*
import lpiemam.com.apppokecards.R.id.filter
import lpiemam.com.apppokecards.model.GlideApp
import lpiemam.com.apppokecards.model.MyGlideAppModule

class AddNewCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var ivCard  : ImageView = itemView.findViewById(R.id.allCardsImageViewCard)

    fun bind(card: Card) {
        GlideApp.with(itemView).load(card.url).placeholder(R.drawable.pokemon_card_back).into(ivCard)
    }
}
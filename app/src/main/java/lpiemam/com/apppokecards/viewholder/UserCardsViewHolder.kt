package lpiemam.com.apppokecards.viewholder

import android.media.Image
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.model.Card
import lpiemam.com.apppokecards.model.MyGlideAppModule

class UserCardsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var ivCard  : ImageView = itemView.findViewById(R.id.collectionImageViewCard)

    fun bind(card: Card) {
//        Glide.with(ivCard).load(card.url).into(ivCard)
        //GlideApp.with(ivPlant).load(userPlant.getPlant().getImageUrl()).placeholder(R.drawable.ic_green_tea)
           // .into(ivPlant)
    }
}
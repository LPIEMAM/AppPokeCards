package lpiemam.com.apppokecards.utils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.squareup.picasso.Picasso
import lpiemam.com.apppokecards.R

/**
 * Created by CarouselView on 7/7/16.
 */
class ImagePanel @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr) {

    private var mPanelContainer: ViewGroup? = null
    private var mImageViewHolder: ImageView? = null

    init {
        initPanelContainer()
        initImagePanel()
    }

    fun setImageUrl(url: String) {
        Picasso.get().load(url).placeholder(R.drawable.pokemon_card_back).into(mImageViewHolder)
    }

    private fun initPanelContainer() {
        val inflater = LayoutInflater.from(context)
        mPanelContainer = inflater.inflate(R.layout.carousel_base_panel, this, true) as ViewGroup
    }

    private fun initImagePanel() {
        mImageViewHolder = ImageView(context)
        mImageViewHolder!!.scaleType = ImageView.ScaleType.FIT_XY
        mImageViewHolder!!.setImageResource(R.mipmap.ic_launcher)
        val lp = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        mImageViewHolder!!.layoutParams = lp
        mPanelContainer!!.addView(mImageViewHolder)
    }
}

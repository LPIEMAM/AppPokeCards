package lpiemam.com.apppokecards.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.pack_opening_dialog_layout.view.*
import lpiemam.com.apppokecards.ImagePanel
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.model.Card

class PackOpeningDialogFragment : DialogFragment() {
    lateinit var listCardsPack : ArrayList<Card>
    private val carouselViews = ArrayList<View>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        val view = activity!!.layoutInflater.inflate(R.layout.pack_opening_dialog_layout, null)
        initStubItems()
        initCarousel(view)
        builder.setTitle("Contenu du Pack")
            .setView(view)
        return builder.create()
    }





    fun initCarousel(view : View) {
        for (stubItem in carouselViews) {
            view.packOpeningCarousel.addView(stubItem)
        }

        view.packOpeningCarousel.notifyDataSetChanged()
    }

    private fun initStubItems() {



        var imagePanel : ImagePanel
        for (card in listCardsPack) {
            imagePanel = ImagePanel(context!!)
            imagePanel.setImageUrl(card.url)
//            imagePanel.setImageResId(R.drawable.pokemon_card_back)
            carouselViews.add(imagePanel)
        }

    }

}
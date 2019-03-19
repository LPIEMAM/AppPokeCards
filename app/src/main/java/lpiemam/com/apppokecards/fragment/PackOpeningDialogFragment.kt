package lpiemam.com.apppokecards.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.pack_opening_dialog_layout.view.*
import lpiemam.com.apppokecards.ImagePanel
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.model.PokemonCard

class PackOpeningDialogFragment : DialogFragment() {

    lateinit var listCardsPack: ArrayList<PokemonCard>
    private val carouselViews = ArrayList<View>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        val view = activity?.layoutInflater?.inflate(R.layout.pack_opening_dialog_layout, null)
        initStubItems()
        initCarousel(view!!)
        builder.setTitle("Contenu du Pack").setView(view)
        return builder.create()
    }

    private fun initCarousel(view: View) {
        for (stubItem in carouselViews) {
            view.packOpeningCarousel.addView(stubItem)
        }
        view.packOpeningCarousel.notifyDataSetChanged()
    }

    private fun initStubItems() {

        var imagePanel: ImagePanel
        for (card in listCardsPack) {
            imagePanel = ImagePanel(context!!)
            if (card.imageUrlHiRes != null) {
                imagePanel.setImageUrl(card.imageUrlHiRes!!)
            }
            carouselViews.add(imagePanel)
        }
    }

}
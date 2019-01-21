package lpiemam.com.apppokecards


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_user_card_detail.*
import lpiemam.com.carousel.CarouselView
import java.util.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class UserCardDetailFragment : Fragment() {

    companion object {

        fun newInstance(): UserCardDetailFragment {
            return UserCardDetailFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_card_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for (stubItem in initStubItems()) {
            userCardDetailCarousel.addView(stubItem)
        }

        userCardDetailCarousel.notifyDataSetChanged()
    }

    private fun initStubItems(): List<View> {
        val result = ArrayList<View>()


        var imagePanel : ImagePanel
        for (i in 0..10) {
            imagePanel = ImagePanel(context!!)
            imagePanel.setImageResId(R.drawable.pokemon_card_back)
            result.add(imagePanel)
        }


        return result
    }


}

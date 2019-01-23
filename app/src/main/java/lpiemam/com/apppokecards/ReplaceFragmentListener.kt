package lpiemam.com.apppokecards

import android.support.v4.app.Fragment
import lpiemam.com.apppokecards.model.Card
import lpiemam.com.apppokecards.model.Pokemon

interface ReplaceFragmentListener {

    fun setDrawerEnabled(enabled: Boolean)
    fun replaceWithUserDetailFragment(card: Card)
    fun setUpBackButton(enabled: Boolean)
    fun replaceWithAddNewCardFragment()
    fun replaceWithFragment(fragment: Fragment, tag : String?)
    fun popBackStack()
}

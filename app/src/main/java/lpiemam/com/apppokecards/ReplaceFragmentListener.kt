package lpiemam.com.apppokecards

import androidx.fragment.app.Fragment
import lpiemam.com.apppokecards.model.Card
import lpiemam.com.apppokecards.model.Pokemon

interface ReplaceFragmentListener {

    fun setDrawerEnabled(enabled: Boolean)
    fun replaceWithUserDetailFragment(card: Card)
    fun setUpBackButton(enabled: Boolean)
    fun replaceWithAddNewCardFragment()
    fun replaceWithQuizzFragment()
    fun replaceWithQuizzStartFragment()
    fun replaceWithQuizzEndedFragment()
    fun replaceWithFragment(fragment: androidx.fragment.app.Fragment, tag : String?)
    fun popBackStack()
    fun notifyCollectionDataSetChanged()

}

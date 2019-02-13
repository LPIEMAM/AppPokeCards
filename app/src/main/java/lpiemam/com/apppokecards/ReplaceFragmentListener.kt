package lpiemam.com.apppokecards

import androidx.fragment.app.Fragment
import lpiemam.com.apppokecards.model.Card
import lpiemam.com.apppokecards.model.Pokemon
import lpiemam.com.apppokecards.model.UserCard

interface ReplaceFragmentListener {

    fun setDrawerEnabled(enabled: Boolean)
    fun setUpBackButton(enabled: Boolean)
    fun replaceWithFullScreenCard(card : Card, boolean: Boolean)
    fun replaceWithCollectionFragment()
    fun replaceWithUserDetailFragment(userCard: UserCard)
    fun replaceWithAllCardsFragment()
    fun replaceWithAllCardsDetailFragment(card: Card)
    fun replaceWithAddNewCardFragment()
    fun replaceWithQuizzFragment()
    fun replaceWithQuizzStartFragment()
    fun replaceWithQuizzEndedFragment()
    fun replaceWithFragment(fragment: androidx.fragment.app.Fragment, tag : String?)
    fun popBackStack()
    fun notifyCollectionDataSetChanged()
    fun goBackFromFullScreenToAllCardsFragment()

}

package lpiemam.com.apppokecards

import lpiemam.com.apppokecards.model.PokemonCard
import lpiemam.com.apppokecards.model.UserCard

interface ReplaceFragmentListener {

    fun setDrawerEnabled(enabled: Boolean)
    fun setUpBackButton(enabled: Boolean)
    fun replaceWithFullScreenCard(pokemonCard : PokemonCard, boolean: Boolean)
    fun replaceWithCollectionFragment()
    fun replaceWithUserDetailFragment(userCard: UserCard)
    fun replaceWithAllCardsFragment()
    fun replaceWithAllCardsDetailFragment(pokemonCard: PokemonCard)
    fun replaceWithAddNewCardFragment()
    fun replaceWithQuizzFragment()
    fun replaceWithQuizzStartFragment()
    fun replaceWithQuizzEndedFragment()
    fun replaceWithFragment(fragment: androidx.fragment.app.Fragment, tag : String?)
    fun popBackStack()
    fun notifyCollectionDataSetChanged()
    fun goBackFromFullScreenToAllCardsFragment()

}

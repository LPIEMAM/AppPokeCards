package lpiemam.com.apppokecards

import lpiemam.com.apppokecards.model.Pokemon

interface ReplaceFragmentListener {

    fun setDrawerEnabled(enabled: Boolean)
    fun showUserCardDetail(pokemon: Pokemon)
    fun setUpBackButton(enabled: Boolean)
}

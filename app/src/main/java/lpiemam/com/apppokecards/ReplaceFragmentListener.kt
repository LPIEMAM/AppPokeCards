package lpiemam.com.apppokecards

import lpiemam.com.apppokecards.model.Card
import lpiemam.com.apppokecards.model.Pokemon

interface ReplaceFragmentListener {

    fun setDrawerEnabled(enabled: Boolean)
    fun showUserCardDetail(card: Card)
    fun setUpBackButton(enabled: Boolean)
}

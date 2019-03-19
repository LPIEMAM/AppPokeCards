package lpiemam.com.apppokecards.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import lpiemam.com.apppokecards.Utils
import lpiemam.com.apppokecards.model.*
import lpiemam.com.apppokecards.retrofit.PokemonCardsRepository
import lpiemam.com.apppokecards.room.DataBaseFactory
import java.util.*
import kotlin.collections.ArrayList

class PokemonCardsViewModel : ViewModel() {

    var pokemonCardsForNameLiveData = MutableLiveData<ArrayList<PokemonCard>>()
    var pokemonCardsForPackLiveData = MutableLiveData<ArrayList<PokemonCard>>()

    var userCardListLiveData = MutableLiveData<ArrayList<UserCard>>()
    var userLiveData = MutableLiveData<User>()

    var userCardList = ArrayList<UserCard>()


    fun initializeData() {
        val liveDataUser = MutableLiveData<User>()
        liveDataUser.observeForever(object : Observer<User> {
            override fun onChanged(it: User?) {
                userLiveData.postValue(it)
                if (UserManager.user == null) {
                    UserManager.user =
                        User(
                            "Annabelle",
                            "Braye",
                            "Siam",
                            "annabelle.braye@gmail.com",
                            Calendar.getInstance(),
                            100000,
                            100000
                        )

                    DataBaseFactory.userCardsDataBase.userDAO().insertUser(UserManager.user!!)

                } else {
                    Log.d("Test", UserManager.user!!.nickName)
                }
                liveDataUser.removeObserver(this)
            }
        })
        DataBaseFactory.userCardsDataBase.userDAO().getUser().observeForever {
            liveDataUser.postValue(it)
        }
        DataBaseFactory.userCardsDataBase.userCardDAO().fetchAll().observeForever {
            val tempUserCardList = ArrayList<UserCard>()
            tempUserCardList.addAll(it)
            userCardListLiveData.postValue(tempUserCardList)
        }


    }

    fun fetchPokemonCardsForName(name: String) {

        PokemonCardsRepository.fetchPokemonCardsForName(name).observeForever {


            pokemonCardsForNameLiveData.postValue(it)

            /*if (it.isEmpty()) {
                pokemonCardsForNameLiveData.postValue(it)
            } else {
                pokemonCardsForNameLiveData.postValue(it)
            }*/
        }
    }

    private fun fetchPokemonCardsForPage(page: Int): MutableLiveData<ArrayList<PokemonCard>> {

        val pokemonCardsForPageLiveData = MutableLiveData<ArrayList<PokemonCard>>()
        PokemonCardsRepository.fetchPokemonCardsForPage(page).observeForever {
            if (it.isEmpty()) {

            } else {
                pokemonCardsForPageLiveData.postValue(it)
            }
        }

        return pokemonCardsForPageLiveData
    }


    fun getCardsPackList(): ArrayList<CardsPack> {
        return Utils.cardsPackList
    }


    fun generateRandomCards(cardsPack: CardsPack) {
        cardsPack.clearCardList()
        for (i in 1..cardsPack.nbCards) {

            var randomPage = (0..22).random()
            var randomPosition = (0..500).random()

            var pokemonCardsForPageLiveData = fetchPokemonCardsForPage(randomPage)
            pokemonCardsForPageLiveData.observeForever(object : Observer<List<PokemonCard>> {
                override fun onChanged(it: List<PokemonCard>?) {
                    if (!it!!.isEmpty()) {
                        cardsPack.listPokemonCards.add(it[randomPosition])
                        if (cardsPack.listPokemonCards.size == cardsPack.nbCards) {
                            pokemonCardsForPackLiveData.postValue(cardsPack.listPokemonCards)
                        }
                        pokemonCardsForPageLiveData.removeObserver(this)
                    }
                }
            })
        }
    }

    fun buyAPack(pack: CardsPack) {
        for (card in pack.listPokemonCards) {

            if (card.isCardInArray(userCardList)) {
                var userCard = card.getInstanceOfUserCard(userCardList)
                userCard.numberOfCard++
            } else {
                userCardList.add(UserCard(card))
            }
        }
        userCardList = ArrayList(userCardList.sortedWith(compareBy { it.pokemonCard.nationalPokedexNumber }))
        UserManager.user!!.coins -= pack.costPack
    }


    fun addUserCard(pokemonCard: PokemonCard) {
        if (pokemonCard.isCardInArray(userCardList)) {
            var userCard = pokemonCard.getInstanceOfUserCard(userCardList)
            userCard.numberOfCard++
        } else {
            userCardList.add(UserCard(pokemonCard))
        }
        userCardList = ArrayList(userCardList.sortedWith(compareBy { it.pokemonCard.nationalPokedexNumber }))
    }

    fun removeUserCard(userCard: UserCard) {
        if (userCard.numberOfCard > 1) {
            userCard.numberOfCard--
        } else {
            userCardList.remove(userCard)
        }
        userCardList = ArrayList(userCardList.sortedWith(compareBy { it.pokemonCard.nationalPokedexNumber }))
    }

    override fun onCleared() {
        saveData()
        super.onCleared()
    }

    fun saveData() {
        DataBaseFactory.userCardsDataBase.userDAO().updateUser(UserManager.user!!)
        DataBaseFactory.userCardsDataBase.userCardDAO().clearTable()
        DataBaseFactory.userCardsDataBase.userCardDAO().insertAll(userCardList)
    }
}
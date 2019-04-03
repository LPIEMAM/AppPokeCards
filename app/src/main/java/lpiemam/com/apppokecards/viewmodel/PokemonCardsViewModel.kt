package lpiemam.com.apppokecards.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import lpiemam.com.apppokecards.Utils
import lpiemam.com.apppokecards.model.*
import lpiemam.com.apppokecards.retrofit.PokemonCardsRepository
import lpiemam.com.apppokecards.room.UserCardsRepository
import java.util.*
import kotlin.collections.ArrayList

class PokemonCardsViewModel : ViewModel() {

    var canClick: Boolean = true

    var selectedUserCardForTrade: UserCard? = null
    var selectedTrade: Trade? = null

    var pokemonCardsForNameLiveData = MutableLiveData<ArrayList<PokemonCard>>()
    var pokemonCardsForPackLiveData = MutableLiveData<ArrayList<PokemonCard>>()
    var currentPage = 1

    var userCardListLiveData = MutableLiveData<ArrayList<UserCard>>()
    var userLiveData = MutableLiveData<User>()

    var userCardList = ArrayList<UserCard>()
    var tradeList = ArrayList<Trade>()



    fun initializeData() {
        fetchUserCardsFromDB()
        fetchUserFromDB()



    }

    fun initDummyTrades() {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = calendar.timeInMillis - 86400000
        val trade = Trade(user1 = User(
            "Test2",
            "User2",
            "YOLO",
            "lpiem@univ-lyon1.fr",
            calendar,
            300000,
            300000
        ), userCard1 =  UserCard(PokemonCard("xyp-XY174", "Pikachu", 25, "130","https://images.pokemontcg.io/xyp/XY174_hires.png", ArrayList(), "XY174", "","", ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList(), 0, "", "", "", "")), user2 = null, userCard2 =  null)
        tradeList.add(trade)
        tradeList.add(trade)
        tradeList.add(trade)
        tradeList.add(trade)
        val tradeUser = Trade(user1 = UserManager.user!!, userCard1 = userCardList[0] , user2 = User(
            "Test2",
            "User2",
            "YOLO",
            "lpiem@univ-lyon1.fr",
            calendar,
            300000,
            300000
        ), userCard2 = UserCard(PokemonCard("xyp-XY174", "Cubone", 25, "130","https://images.pokemontcg.io/dp6/90_hires.png",
            java.util.ArrayList(), "XY174", "","",
            java.util.ArrayList(),
            java.util.ArrayList(),
            java.util.ArrayList(),
            java.util.ArrayList(),
            java.util.ArrayList(), 0, "", "", "", "")))
        //tradeList.add(tradeUser)
    }

    fun fetchUserCardsFromDB() {
        UserCardsRepository.fetchUserCards().observeForever {
            userCardListLiveData.postValue(it)
        }
    }

    fun insertCardToDB(userCard: UserCard) {
        UserCardsRepository.insertCard(userCard)
    }

    fun updateCardInDB(userCard: UserCard) {
        UserCardsRepository.updateCard(userCard)
    }

    fun deleteCardFromDB(id: Int) {
        UserCardsRepository.deleteCard(id)
    }

    fun fetchUserFromDB() {
        UserCardsRepository.fetchUser().observeForever {
            userLiveData.postValue(it)
        }
    }

    fun saveUserToDB(user: User) {
        UserCardsRepository.saveUser(user)
    }

    fun updateUserInDB(user: User) {
        UserCardsRepository.updateUser(user)
    }

    fun fetchPokemonCardsForName(name: String) {
        currentPage = 1
        PokemonCardsRepository.fetchPokemonCardsForName(1, name).observeForever {

            pokemonCardsForNameLiveData.postValue(it)
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

    fun fetchPokemonCardsForNextPage(name: String) {
        currentPage++
        PokemonCardsRepository.fetchPokemonCardsForName(currentPage, name).observeForever {
            pokemonCardsForNameLiveData.value?.addAll(it)
        }
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

            var userCard: UserCard?
            if (card.isCardInArray(userCardList)) {
                userCard = card.getInstanceOfUserCard(userCardList)
                userCard.numberOfCard++
                insertCardToDB(userCard)
            } else {
                userCard = UserCard(card)
                userCardList.add(userCard)
                insertCardToDB(userCard)
            }
        }
        userCardList.sort()
        UserManager.user!!.coins -= pack.costPack
        updateUserInDB(UserManager.user!!)
    }


    fun addUserCard(pokemonCard: PokemonCard) {
        var userCard: UserCard?
        if (pokemonCard.isCardInArray(userCardList)) {
            userCard = pokemonCard.getInstanceOfUserCard(userCardList)
            userCard.numberOfCard++
            insertCardToDB(userCard)
        } else {
            userCard = UserCard(pokemonCard)
            userCardList.add(userCard)
            insertCardToDB(userCard)
        }
        userCardList.sort()
        UserManager.user!!.dusts -= pokemonCard.getCostToCraft()
        updateUserInDB(UserManager.user!!)
    }

    fun removeUserCard(userCard: UserCard) {
        if (userCard.numberOfCard > 1) {
            userCard.numberOfCard--
            if (userCard.userCardID != 0) {
                updateCardInDB(userCard)
            }
        } else {
            userCardList.remove(userCard)
            if (userCard.userCardID != 0) {
                deleteCardFromDB(userCard.userCardID)
            }
        }
        userCardList.sort()
        UserManager.user!!.dusts += userCard.pokemonCard.getCostForDecraft()
        updateUserInDB(UserManager.user!!)
    }
}
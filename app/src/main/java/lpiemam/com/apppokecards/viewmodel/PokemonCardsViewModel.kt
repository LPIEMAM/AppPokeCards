package lpiemam.com.apppokecards.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import lpiemam.com.apppokecards.Utils
import lpiemam.com.apppokecards.model.*
import lpiemam.com.apppokecards.retrofit.PokemonCardsRepository
import lpiemam.com.apppokecards.room.DataBaseFactory
import lpiemam.com.apppokecards.room.UserCardsRepository
import kotlin.collections.ArrayList

class PokemonCardsViewModel : ViewModel() {

    var pokemonCardsForNameLiveData = MutableLiveData<ArrayList<PokemonCard>>()
    var pokemonCardsForPackLiveData = MutableLiveData<ArrayList<PokemonCard>>()
    var currentPage = 1

    var userCardListLiveData = MutableLiveData<ArrayList<UserCard>>()
    var userLiveData = MutableLiveData<User>()

    var userCardList = ArrayList<UserCard>()


    fun initializeData() {
        fetchUserFromDB()
        fetchUserCardsFromDB()
//        val liveDataUser = MutableLiveData<User>()
//        liveDataUser.observeForever(object : Observer<User> {
//            override fun onChanged(it: User?) {
//                if (it == null) {
//                    val user =
//                        User(
//                            "Annabelle",
//                            "Braye",
//                            "Siam",
//                            "annabelle.braye@gmail.com",
//                            Calendar.getInstance(),
//                            100000,
//                            100000
//                        )
//
//                    DataBaseFactory.userCardsDataBase.userDAO().saveUser(user)
//                    userLiveData.postValue(user)
//
//                } else {
//                    userLiveData.postValue(it)
//                    Log.d("Test", it.nickName)
//                }
//                liveDataUser.removeObserver(this)
//            }
//        })
//        DataBaseFactory.userCardsDataBase.userDAO().fetchUser().observeForever {
//            liveDataUser.postValue(it)
//        }

//        DataBaseFactory.userCardsDataBase.userCardDAO().fetchAll().observeForever {
//            val tempUserCardList = ArrayList<UserCard>()
//            tempUserCardList.addAll(it)
//            userCardListLiveData.postValue(tempUserCardList)
//        }


    }

    fun fetchUserCardsFromDB() {
        UserCardsRepository.fetchUserCards().observeForever {
            userCardListLiveData.postValue(it)
        }
    }

    fun saveUserCardsToDB() {
        UserCardsRepository.saveUserCards(userCardList)
    }

    fun clearUserCards() {
        UserCardsRepository.clearUserCards()
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

            if (card.isCardInArray(userCardList)) {
                var userCard = card.getInstanceOfUserCard(userCardList)
                userCard.numberOfCard++
            } else {
                userCardList.add(UserCard(card))
            }
        }
        userCardList.sort()
        UserManager.user!!.coins -= pack.costPack
    }


    fun addUserCard(pokemonCard: PokemonCard) {
        if (pokemonCard.isCardInArray(userCardList)) {
            var userCard = pokemonCard.getInstanceOfUserCard(userCardList)
            userCard.numberOfCard++
        } else {
            userCardList.add(UserCard(pokemonCard))
        }
        userCardList.sort()
    }

    fun removeUserCard(userCard: UserCard) {
        if (userCard.numberOfCard > 1) {
            userCard.numberOfCard--
        } else {
            userCardList.remove(userCard)
        }
        userCardList.sort()
    }

    override fun onCleared() {
        saveData()
        super.onCleared()
    }

    fun saveData() {
        updateUserInDB(UserManager.user!!)
        clearUserCards()
        saveUserCardsToDB()
    }
}
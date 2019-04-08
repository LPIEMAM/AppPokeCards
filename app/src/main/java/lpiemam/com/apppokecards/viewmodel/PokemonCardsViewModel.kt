package lpiemam.com.apppokecards.viewmodel

//import lpiemam.com.apppokecards.room.UserCardsRepository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import lpiemam.com.apppokecards.utils.Utils
import lpiemam.com.apppokecards.model.*
import lpiemam.com.apppokecards.retrofit.pokemoncards.PokemonCardsRepository
import lpiemam.com.apppokecards.retrofit.trades.TradeRepository
import lpiemam.com.apppokecards.retrofit.usercards.UserCardsRepository
import lpiemam.com.apppokecards.retrofit.users.UsersRepository

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

    var tradeForUserList = MutableLiveData<ArrayList<Trade>>()
    var currentTradeForUser = MutableLiveData<Trade>()

    var affectedRowsUserCardsLiveData = MutableLiveData<Int>()

    var userLookingForTrade = false

    fun initializeData() {
        //fetchUserCardsForName("null")
    }


    fun fetchUserCardsForName(name: String) {
        UserCardsRepository.fetchUserCardsForName(UserManager.loggedUser!!.userId, name).observeForever {
            userCardListLiveData.postValue(it)
        }
    }


    fun insertCardToDB(userCard: UserCard) {
        UserCardsRepository.postUserCard(userCard).observeForever {
            userCard.userCardID = it
        }
    }

    fun updateCardInDB(userCard: UserCard) {
        val rowsUpdatedLiveData = UserCardsRepository.patchUserCard(userCard)
        rowsUpdatedLiveData.observeForever(object : Observer<Boolean> {
            override fun onChanged(it: Boolean?) {
                if (it == true) {
                    if (affectedRowsUserCardsLiveData.value == null) {
                        affectedRowsUserCardsLiveData.value = 1
                    } else {
                        affectedRowsUserCardsLiveData.postValue(affectedRowsUserCardsLiveData.value!! + 1)
                    }
                    rowsUpdatedLiveData.removeObserver(this)
                }
            }

        })
    }

    fun deleteCardFromDB(userCard: UserCard) {
        val rowDeletedLiveData = UserCardsRepository.deleteUserCard(userCard)
        rowDeletedLiveData.observeForever(object : Observer<Boolean> {
            override fun onChanged(it: Boolean?) {
                if (it == true) {
                    if (affectedRowsUserCardsLiveData.value == null) {
                        affectedRowsUserCardsLiveData.value = 1
                    } else {
                        affectedRowsUserCardsLiveData.postValue(affectedRowsUserCardsLiveData.value!! + 1)
                    }
                    rowDeletedLiveData.removeObserver(this)
                }
            }

        })
    }

    fun updateUserInDB(user: User) {
        UsersRepository.patchUser(user)
    }

    fun postTrade(trade: Trade) {
        TradeRepository.postTrade(trade).observeForever {
            trade.id = it
        }
    }

    fun patchTrade(trade: Trade) {
        TradeRepository.patchTrade(trade)
    }


    fun patchTradeValidated(trade: Trade) {
        TradeRepository.patchTradeValidated(trade)
    }

    fun deleteTrade(trade: Trade) {
        TradeRepository.deleteTrade(trade)
    }

    fun getTradesForUser(user: User) {
        TradeRepository.getTradesForUser(user).observeForever {
            tradeForUserList.postValue(it)
        }
    }

    fun getCurrentTradeForUser(user: User) {
        TradeRepository.getCurrentTradeForUser(user).observeForever {
            if (!it.isEmpty()) {
                currentTradeForUser.postValue(it[0])
            } else {
                currentTradeForUser.postValue(null)
            }
        }
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
            var randomPosition = 0
            if (randomPage != 22) {
                randomPosition = (0..500).random()
            } else {
                randomPosition = (0..416).random()
            }

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
                userCard.numberOfCardAvailable++
                updateCardInDB(userCard)
            } else {
                userCard = UserCard(card)
                userCardList.add(userCard)
                insertCardToDB(userCard)
            }
        }
        userCardList.sort()
        UserManager.loggedUser!!.coins -= pack.costPack
        updateUserInDB(UserManager.loggedUser!!)
    }

    fun addUserCardForUser(userId: Int, pokemonCard: PokemonCard) {
        UserCardsRepository.fetchUserCardsForName(userId, "null").observeForever {
            var userCard: UserCard?
            if (pokemonCard.isCardInArray(it)) {
                userCard = pokemonCard.getInstanceOfUserCard(it)
                userCard.numberOfCard++
                userCard.numberOfCardAvailable++
                updateCardInDB(userCard)
            } else {
                userCard = UserCard(pokemonCard)
                userCard.userId = userId
                if (userId == UserManager.loggedUser!!.userId) {
                    userCardList.add(userCard)
                }
                insertCardToDB(userCard)
            }
        }
    }

    fun removeUserCardForUser(userId: Int, userCard: UserCard) {

        if (userCard.numberOfCard > 1) {
            userCard.numberOfCard--
            userCard.numberOfCardAvailable--
            if (userCard.userCardID != 0) {
                updateCardInDB(userCard)
            }

            if (userId == UserManager.loggedUser!!.userId) {
                val userCardToUpdate = userCard.pokemonCard.getInstanceOfUserCard(userCardList)
                userCardToUpdate.numberOfCard--
                userCardToUpdate.numberOfCardAvailable--
            }
        } else {
            if (userCard.userCardID != 0) {
                deleteCardFromDB(userCard)
            }
            if (userId == UserManager.loggedUser!!.userId) {
                val userCardToRemove = userCard.pokemonCard.getInstanceOfUserCard(userCardList)
                userCardList.remove(userCardToRemove)
            }
        }

    }

    fun addUserCard(pokemonCard: PokemonCard) {
        var userCard: UserCard?

        if (pokemonCard.isCardInArray(userCardList)) {
            userCard = pokemonCard.getInstanceOfUserCard(userCardList)
            userCard.numberOfCard++
            userCard.numberOfCardAvailable++
            updateCardInDB(userCard)
        } else {
            userCard = UserCard(pokemonCard)
            userCardList.add(userCard)
            insertCardToDB(userCard)
        }
        userCardList.sort()
        UserManager.loggedUser!!.dusts -= pokemonCard.getCostToCraft()
        updateUserInDB(UserManager.loggedUser!!)
    }

    fun removeUserCard(userCard: UserCard) {
        if (userCard.numberOfCard > 1) {
            userCard.numberOfCard--
            userCard.numberOfCardAvailable--
            if (userCard.userCardID != 0) {
                updateCardInDB(userCard)
            }
        } else {
            userCardList.remove(userCard)
            if (userCard.userCardID != 0) {
                deleteCardFromDB(userCard)
            }
        }
        userCardList.sort()
        UserManager.loggedUser!!.dusts += userCard.pokemonCard.getCostForDecraft()
        updateUserInDB(UserManager.loggedUser!!)
    }
}
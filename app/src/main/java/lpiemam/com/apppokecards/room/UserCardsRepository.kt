package lpiemam.com.apppokecards.room

import androidx.lifecycle.MutableLiveData
import lpiemam.com.apppokecards.model.User
import lpiemam.com.apppokecards.model.UserCard

object UserCardsRepository {
    var userCardListLiveData = MutableLiveData<ArrayList<UserCard>>()
    var userLiveData = MutableLiveData<User>()

    fun fetchUserCards() : MutableLiveData<ArrayList<UserCard>> {

        DataBaseFactory.userCardsDataBase.userCardDAO().fetchAll().observeForever {
            val tempUserCardList = ArrayList<UserCard>()
            tempUserCardList.addAll(it)
            userCardListLiveData.postValue(tempUserCardList)
        }

        return userCardListLiveData
    }

    fun saveUserCards(userCards: ArrayList<UserCard>) {
        DataBaseFactory.userCardsDataBase.userCardDAO().saveAll(userCards)
    }

    fun clearUserCards() {
        DataBaseFactory.userCardsDataBase.userCardDAO().clearTable()
    }

    fun fetchUser() : MutableLiveData<User> {

        DataBaseFactory.userCardsDataBase.userDAO().fetchUser().observeForever {
            userLiveData.postValue(it)
        }

        return userLiveData
    }

    fun saveUser(user: User) {
        DataBaseFactory.userCardsDataBase.userDAO().saveUser(user)
    }

    fun updateUser(user: User) {
        DataBaseFactory.userCardsDataBase.userDAO().updateUser(user)
    }
}
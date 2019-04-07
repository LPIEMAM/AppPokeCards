package lpiemam.com.apppokecards.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import lpiemam.com.apppokecards.model.User
import lpiemam.com.apppokecards.retrofit.users.UsersRepository

class UsersViewModel : ViewModel() {

//    var usersLiveData = MutableLiveData<ArrayList<User>>()


    fun fetchUsers(): MutableLiveData<ArrayList<User>> {

        val usersLiveData = MutableLiveData<ArrayList<User>>()
        UsersRepository.fetchUsers().observeForever {
            if (it.isEmpty()) {

            } else {
                usersLiveData.postValue(it)
            }
        }

        return usersLiveData
    }

    fun updateUserInDB(user: User) {
        UsersRepository.patchUser(user)
    }

}
package lpiemam.com.apppokecards.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_connection.*
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.model.User
import lpiemam.com.apppokecards.model.UserManager
import lpiemam.com.apppokecards.viewmodel.UsersViewModel

class ConnectionActivity : AppCompatActivity() {

    var userViewModel: UsersViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connection)

        userViewModel = ViewModelProviders.of(this).get(UsersViewModel::class.java)

        userViewModel?.fetchUsers()!!.observe(this, androidx.lifecycle.Observer {
            val spinnerAdapter = ArrayAdapter<User>(this, android.R.layout.simple_spinner_dropdown_item)
            spinnerAdapter.addAll(it)
            usersSpinner.adapter = spinnerAdapter
            spinnerAdapter.notifyDataSetChanged()
        })


        buttonPickUser.setOnClickListener {
            val selectedUser = usersSpinner.selectedItem as User
            UserManager.loggedUser = selectedUser
            Log.d("Connection : ", UserManager.loggedUser!!.toString())
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

}

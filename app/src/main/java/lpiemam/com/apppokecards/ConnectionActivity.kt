package lpiemam.com.apppokecards

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_connection.*
import kotlinx.android.synthetic.main.activity_connection.view.*
import kotlinx.android.synthetic.main.fragment_quizz_start.view.*
import lpiemam.com.apppokecards.model.User
import lpiemam.com.apppokecards.model.UserManager
import lpiemam.com.apppokecards.viewmodel.UsersViewModel
import timber.log.Timber
import java.util.*

class ConnectionActivity : AppCompatActivity() {

    var userViewModel: UsersViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connection)

        userViewModel = ViewModelProviders.of(this).get(UsersViewModel::class.java)

        userViewModel?.fetchUsers()!!.observe(this, androidx.lifecycle.Observer{
            val spinnerAdapter = ArrayAdapter<User>(this, android.R.layout.simple_spinner_dropdown_item)
            spinnerAdapter.addAll(it)
            usersSpinner.adapter = spinnerAdapter
            spinnerAdapter.notifyDataSetChanged()
        })


        buttonPickUser.setOnClickListener {
            val selectedUser = usersSpinner.selectedItem as User
            UserManager.user = selectedUser
            Log.d("Connection : ", UserManager.user!!.toString())
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

}

package lpiemam.com.apppokecards

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.shobhitpuri.custombuttons.GoogleSignInButton
import com.squareup.picasso.Picasso
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var callbackManager: CallbackManager
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var tvLoggedInUserName: TextView
    lateinit var ivLoggedInUserPicture: ImageView
    var isUserLoggedOnGoogle = false
    var isUserLoggedOnFacebook = false
    lateinit var signInButton: GoogleSignInButton
    lateinit var texteEntrezId: EditText
    lateinit var buttonValidate: Button
    lateinit var tvUserName: TextView
    lateinit var loggedUser: UserInfo
    lateinit var facebook_login_button: LoginButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvLoggedInUserName = findViewById(R.id.tvLoggedInUserName)
        ivLoggedInUserPicture = findViewById(R.id.ivLoggedInUserPicture)

        texteEntrezId = findViewById(R.id.texteEntrerId)
        buttonValidate = findViewById(R.id.boutonValiderId)
        tvUserName = findViewById(R.id.tvPseudoUser)
        signInButton = findViewById(R.id.sign_in_button)
        facebook_login_button = findViewById(R.id.login_button);

        loggedUser = UserInfo()

        signInButton.setOnClickListener {
            Log.d(TAG, "onClick: Test")
            if (!isUserLoggedOnGoogle && !isUserLoggedOnFacebook) {
                signIn()
                signInButton.text = "Sign Out"
                LoginManager.getInstance().unregisterCallback(callbackManager)
                isUserLoggedOnGoogle = true
            } else if (!isUserLoggedOnFacebook) {
                signOut()
                signInButton.text = "Sign In"
                isUserLoggedOnGoogle = false
            }
        }

        /** Facebook Configuration  */

        val accessToken = AccessToken.getCurrentAccessToken()

        val acct = GoogleSignIn.getLastSignedInAccount(this)

        initLogIns(accessToken, acct)

        callbackManager = CallbackManager.Factory.create()

        facebook_login_button.setReadPermissions(Arrays.asList("user_status"))

        LoginManager.getInstance().logInWithReadPermissions(
                this,
                Arrays.asList("email"))

        LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        Log.d("FBLOGIN", loginResult.accessToken.token.toString())
                        Log.d("FBLOGIN", loginResult.recentlyDeniedPermissions.toString())
                        Log.d("FBLOGIN", loginResult.recentlyGrantedPermissions.toString())


                        val request = GraphRequest.newMeRequest(loginResult.accessToken) { infos, response ->
                            try {
                                //here is the data that you want
                                loggedUser.firstName = infos.getString("first_name")
                                loggedUser.lastName = infos.getString("last_name")
                                loggedUser.facebookPhoto = infos.getJSONObject("picture").getJSONObject("data").getString("url")

                                if (infos.has("id")) {
                                    tvLoggedInUserName.setText(loggedUser.firstName + " " + loggedUser.lastName)
                                    Picasso.get().load(loggedUser.facebookPhoto).into(ivLoggedInUserPicture)
                                } else {
                                    Log.e("FBLOGIN_FAILD", infos.toString())
                                }

                            } catch (e: Exception) {
                                e.printStackTrace()
                                //dismissDialogLogin()
                            }
                        }

                        val parameters = Bundle()
                        parameters.putString("fields", "first_name,last_name,email,id,picture.type(large)")
                        request.parameters = parameters
                        request.executeAsync()

                    }

                    override fun onCancel() {
                        Log.e("FBLOGIN_FAILD", "Cancel")
                    }

                    override fun onError(error: FacebookException) {
                        Log.e("FBLOGIN_FAILD", "ERROR", error)
                    }
                })

        //LoginManager.getInstance().unregisterCallback(callbackManager)
        /** End Facebook Configuration  */

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        // Set the dimensions of the sign-in button.


    }

    private fun initLogIns(accessToken: AccessToken?, googleAccount: GoogleSignInAccount?) {
        if (accessToken != null && !accessToken.isExpired) {
            Log.d("FBLOGIN", accessToken.token.toString())

            val request = GraphRequest.newMeRequest(accessToken) { infos, response ->
                try {
                    //here is the data that you want
                    loggedUser.firstName = infos.getString("first_name")
                    loggedUser.lastName = infos.getString("last_name")
                    loggedUser.facebookPhoto = infos.getJSONObject("picture").getJSONObject("data").getString("url")

                    if (infos.has("id")) {
                        tvLoggedInUserName.setText(loggedUser.firstName + " " + loggedUser.lastName)
                        Picasso.get().load(loggedUser.facebookPhoto).into(ivLoggedInUserPicture)
                    } else {
                        Log.e("FBLOGIN_FAILD", infos.toString())
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                    //dismissDialogLogin()
                }
            }

            val parameters = Bundle()
            parameters.putString("fields", "first_name,last_name,email,id,picture.type(large)")
            request.parameters = parameters
            request.executeAsync()

            isUserLoggedOnFacebook = true
        } else if (googleAccount != null) {


            loggedUser.firstName = googleAccount.givenName
            loggedUser.lastName = googleAccount.familyName
            loggedUser.eMail = googleAccount.email
            loggedUser.googleId = googleAccount.id
            loggedUser.googlePhoto = googleAccount.photoUrl.toString()
            tvLoggedInUserName.text = loggedUser.firstName + " " + loggedUser.lastName
            Picasso.get().load(loggedUser.googlePhoto).into(ivLoggedInUserPicture)
            isUserLoggedOnGoogle = true

            signInButton!!.text = "Sign Out"
        }

    }

    override fun onStart() {
        super.onStart()
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        val account = GoogleSignIn.getLastSignedInAccount(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }

    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun signOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener(this) {
            tvLoggedInUserName.text = ""
            Picasso.get().load("@drawable/ic_launcher_background").into(ivLoggedInUserPicture)
        }
    }


    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult<ApiException>(ApiException::class.java!!)
            if (account != null) {
                val personName = account.displayName
                val personGivenName = account.givenName
                val personFamilyName = account.familyName
                val personEmail = account.email
                val personId = account.id
                val personPhoto = account.photoUrl
                tvLoggedInUserName.text = personName
                Picasso.get().load(personPhoto).into(ivLoggedInUserPicture)
                Log.d(TAG, "handleSignInResult: " + personName!!)
            }

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
        }

    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.boutonValiderId -> {
                val user = UserPokeCards(Integer.parseInt(texteEntrezId.text.toString()), this)
                user.start()
            }
        }
    }

    fun callBack(user: UserInfo) {
        tvUserName.text = user.pseudo
    }

    companion object {

        private val RC_SIGN_IN = 1
        private val TAG = "MainActivity"
    }
}

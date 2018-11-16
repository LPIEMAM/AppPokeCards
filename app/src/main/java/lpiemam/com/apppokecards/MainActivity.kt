package lpiemam.com.apppokecards

import android.content.Intent
import android.net.Uri
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
import com.facebook.GraphResponse
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.shobhitpuri.custombuttons.GoogleSignInButton
import com.squareup.picasso.Picasso

import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    lateinit var callbackManager: CallbackManager
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var tvName: TextView
    lateinit var ivImage: ImageView
    var isUserLoggedOnGoogle = false
    var isUserLoggedOnFacebook = false
    lateinit  var signInButton: GoogleSignInButton
    lateinit var texteEntrezId: EditText
    lateinit var buttonValidate: Button
    lateinit var tvUserName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvName = findViewById(R.id.tvName)
        ivImage = findViewById(R.id.ivImage)

        texteEntrezId = findViewById(R.id.texteEntrerId)
        buttonValidate = findViewById(R.id.boutonValiderId)
        tvUserName = findViewById(R.id.tvPseudoUser)

        /** Facebook Configuration  */

        val accessToken = AccessToken.getCurrentAccessToken()

        val acct = GoogleSignIn.getLastSignedInAccount(this)

        initLogIns(accessToken, acct)

        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        val request = GraphRequest.newMeRequest(accessToken) { `object`, response ->
                            try {
                                tvName.text = `object`.getString("id")
                                Picasso.get().load("https://graph.facebook.com/" + `object`.getString("id") + "/picture?type=large").into(ivImage)

                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        }
                        isUserLoggedOnFacebook = true
                    }

                    override fun onCancel() {
                        isUserLoggedOnFacebook = false
                    }

                    override fun onError(exception: FacebookException) {
                        // App code
                    }
                })

        LoginManager.getInstance().unregisterCallback(callbackManager)
        /** End Facebook Configuration  */

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        // Set the dimensions of the sign-in button.
        signInButton = findViewById(R.id.sign_in_button)
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

    }

    private fun initLogIns(accessToken: AccessToken?, acct: GoogleSignInAccount?) {
        if (accessToken != null && !accessToken.isExpired) {
            val request = GraphRequest.newMeRequest(accessToken) { `object`, response ->
                try {
                    tvName.text = `object`.getString("id")
                    Picasso.get().load("https://graph.facebook.com/" + `object`.getString("id") + "/picture?type=large").into(ivImage)

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            isUserLoggedOnFacebook = true
        } else if (acct != null) {

            val personName = acct.displayName
            val personGivenName = acct.givenName
            val personFamilyName = acct.familyName
            val personEmail = acct.email
            val personId = acct.id
            val personPhoto = acct.photoUrl
            tvName.text = personName
            Picasso.get().load(personPhoto).into(ivImage)
            isUserLoggedOnGoogle = true

            signInButton.text = "Sign Out"
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
            tvName.text = ""
            Picasso.get().load("@drawable/ic_launcher_background").into(ivImage)
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
                tvName.text = personName
                Picasso.get().load(personPhoto).into(ivImage)
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

    fun callBack(idUser: String) {
        tvUserName.text = idUser
    }

    companion object {

        private val RC_SIGN_IN = 1
        private val TAG = "MainActivity"
    }
}

package lpiemam.com.apppokecards;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.shobhitpuri.custombuttons.GoogleSignInButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1;
    private static final String TAG = "MainActivity";
    CallbackManager callbackManager;
    GoogleSignInClient mGoogleSignInClient;
    TextView tvName;
    ImageView ivImage;
    boolean isUserLoggedOnGoogle = false;
    boolean isUserLoggedOnFacebook = false;
    GoogleSignInButton signInButton;
    EditText texteEntrezId;
    Button buttonValidate;
    TextView tvUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvName = findViewById(R.id.tvName);
        ivImage = findViewById(R.id.ivImage);

        texteEntrezId =findViewById(R.id.texteEntrerId);
        buttonValidate=findViewById(R.id.boutonValiderId);
        tvUserName=findViewById(R.id.tvPseudoUser);

        /** Facebook Configuration **/

        final AccessToken accessToken = AccessToken.getCurrentAccessToken();

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        //initLogIns(accessToken, acct);

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    tvName.setText(object.getString("id"));
                                    Picasso.get().load("https://graph.facebook.com/" + object.getString("id")+ "/picture?type=large").into(ivImage);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        isUserLoggedOnFacebook = true;
                    }

                    @Override
                    public void onCancel() {
                        isUserLoggedOnFacebook = false;
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

        LoginManager.getInstance().unregisterCallback(callbackManager);
        /** End Facebook Configuration **/

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // Set the dimensions of the sign-in button.
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Test");
                if (! isUserLoggedOnGoogle && !isUserLoggedOnFacebook) {
                    signIn();
                    signInButton.setText("Sign Out");
                    LoginManager.getInstance().unregisterCallback(callbackManager);
                    isUserLoggedOnGoogle = true;
                } else if (!isUserLoggedOnFacebook){
                    signOut();
                    signInButton.setText("Sign In");
                    isUserLoggedOnGoogle = false;
                }
            }
        });

    }

    private void initLogIns(AccessToken accessToken, GoogleSignInAccount acct){
        if (accessToken != null && !accessToken.isExpired()) {
            GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    try {
                        tvName.setText(object.getString("id"));
                        Picasso.get().load("https://graph.facebook.com/" + object.getString("id")+ "/picture?type=large").into(ivImage);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            isUserLoggedOnFacebook = true;
        } else if (acct != null){

            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            tvName.setText(personName);
            Picasso.get().load(personPhoto).into(ivImage);
            isUserLoggedOnGoogle = true;

            signInButton.setText("Sign Out");
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                tvName.setText("");
                Picasso.get().load("@drawable/ic_launcher_background").into(ivImage);
            }
        });
    }



    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                String personName = account.getDisplayName();
                String personGivenName = account.getGivenName();
                String personFamilyName = account.getFamilyName();
                String personEmail = account.getEmail();
                String personId = account.getId();
                Uri personPhoto = account.getPhotoUrl();
                tvName.setText(personName);
                Picasso.get().load(personPhoto).into(ivImage);
                Log.d(TAG, "handleSignInResult: " + personName);
            }

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.boutonValiderId :
                UserPokeCards user = new UserPokeCards(Integer.parseInt(texteEntrezId.getText().toString()), this);
                user.start();
                break;
        }
    }

    public void callBack(String idUser) {
        tvUserName.setText(idUser);
    }
}

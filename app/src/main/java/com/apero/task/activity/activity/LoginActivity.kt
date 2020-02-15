package com.apero.task.activity.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.apero.task.R
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    var mAuth = FirebaseAuth.getInstance()
    private val RC_SIGN_IN = 9001
    private var mGoogleApiClient: GoogleApiClient? = null
    val TAG: String = "GoogleSignIn"
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initializeUI()

    }

    private fun initializeUI() {

        val loginBtn = findViewById<View>(R.id.loginBtn) as Button
        val registerTxt = findViewById<View>(R.id.regTxt) as TextView
        auth = FirebaseAuth.getInstance()
        setupSignIn()

        if (mAuth.currentUser != null) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        btn_signin_sign_in.setOnClickListener {
            signIn()
            Toast.makeText(this, "Please Wait!", Toast.LENGTH_SHORT).show();

        }

        loginBtn.setOnClickListener {
            login()
        }

        registerTxt.setOnClickListener {
            register()
        }


    }

    private fun setupSignIn() {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(
                this
            ) { Toast.makeText(this, "Login Error!", Toast.LENGTH_SHORT).show() }
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()
    }

    private fun signIn() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {


                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser

                    updateUI()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
//                    Snackbar.make(main_layout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
//                    updateUI(null)
                }

                // ...
            }
    }

    private fun updateUI() {
        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
        Toast.makeText(this, "Successfully Logged in :)", Toast.LENGTH_LONG).show()
        finish()
    }

    private fun login() {
        val emailTxt = findViewById<View>(R.id.emailTxt) as EditText
        var email = emailTxt.text.toString()
        val passwordTxt = findViewById<View>(R.id.passwordTxt) as EditText
        var password = passwordTxt.text.toString()

        if (!email.isEmpty() && !password.isEmpty()) {
            this.mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this,
                { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                        Toast.makeText(this, "Successfully Logged in :)", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "Error Logging in :(", Toast.LENGTH_SHORT).show()
                    }
                })

        } else {
            Toast.makeText(this, "Please fill up the Credentials :|", Toast.LENGTH_SHORT).show()
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)

            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // ...
            }
        }
    }

    private fun register() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }


}

package com.apero.task.activity.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.apero.task.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()
    lateinit var mDatabase: DatabaseReference
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var auth: FirebaseAuth
    val RC_SIGN_IN = 123
    val TAG: String = "GoogleSignIn"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initView()
    }

    private fun initView() {
        val registerBtn = findViewById<View>(R.id.regBtn) as Button

        mDatabase = FirebaseDatabase.getInstance().getReference("Names")

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        auth = FirebaseAuth.getInstance()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        button.setOnClickListener {
            signIn()
        }
        registerBtn.setOnClickListener {
            registerUser()
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
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

    private fun registerUser() {
        val emailTxt = findViewById<View>(R.id.emailTxt) as EditText
        val passwordTxt = findViewById<View>(R.id.passwordTxt) as EditText
        val nameTxt = findViewById<View>(R.id.nameTxt) as EditText

        var email = emailTxt.text.toString()
        var password = passwordTxt.text.toString()
        var name = nameTxt.text.toString()

        if (!email.isEmpty() && !password.isEmpty() && !name.isEmpty()) {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this,
                { task ->
                    if (task.isSuccessful) {
                        val user = mAuth.currentUser
                        val uid = user!!.uid
                        mDatabase.child(uid).child("Name").setValue(name)
                        startActivity(Intent(this, HomeActivity::class.java))
                        Toast.makeText(this, "Successfully registered :)", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(
                            this,
                            "Error registering, try again later :(",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
        } else {
            Toast.makeText(this, "Please fill up the Credentials :|", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateUI() {
        startActivity(Intent(this@RegisterActivity, HomeActivity::class.java))
        Toast.makeText(this, "Successfully signed in :)", Toast.LENGTH_LONG).show()
        finish()
    }

}

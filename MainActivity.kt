package com.example.snapchatclone

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    var emailEditText: EditText? = null
    var passwordEditText: EditText? = null
    val mAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)


        if(mAuth.currentUser != null) {
            login()
        }

    }

    fun goClicked(view: View) {
        //Check if we can log in the user
        mAuth.signInWithEmailAndPassword(emailEditText?.text.toString(), passwordEditText?.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    login()
                } else {
                    //if it doesn't work -> sign up
                    mAuth.createUserWithEmailAndPassword(emailEditText?.text.toString(), passwordEditText?.text.toString()).addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            //FirebaseDatabase.getInstance().getReference().child("users").child("email").push().setValue("123").
                            FirebaseDatabase.getInstance().reference.child("users").child(task.result?.user!!.uid).child("email").setValue(emailEditText?.text.toString()).addOnSuccessListener {
                                Toast.makeText(this,"SUCCESS", Toast.LENGTH_SHORT).show()

                            }.addOnFailureListener{
                                Toast.makeText(this,"FAIL",Toast.LENGTH_SHORT).show()
                            }
                            login()
                        } else {
                            Toast.makeText(this,"Login, failed. Try Again.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }


    }

    fun login() {
        //move to next activity
        val intent = Intent(this, SnapsActivity:: class.java)
        startActivity(intent)
    }
}
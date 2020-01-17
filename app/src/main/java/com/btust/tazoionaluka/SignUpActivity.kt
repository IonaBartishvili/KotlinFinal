package com.btust.tazoionaluka

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")


        signUpBtn.setOnClickListener {
            var first_name = fName_input.text.toString()
            var last_name = lName_input.text.toString()
            var email = email_input.text.toString()
            var password = password_input.text.toString()
//            var userID = auth.currentUser!!.uid

            if (TextUtils.isEmpty(first_name) || TextUtils.isEmpty(last_name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "გთხოვთ შეავსოთ ყველა ველი", Toast.LENGTH_LONG).show()

            } else {

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, OnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this,
                                "რეგისტრაცია წარმატებით განხორციელდა",
                                Toast.LENGTH_LONG
                            ).show()
                            writeUser(auth.currentUser!!.uid, first_name, last_name, email)
                            var gotoLoginActivity = Intent(this, LoginActivity::class.java)
                            startActivity(gotoLoginActivity)
                        } else {
                            Toast.makeText(this, "შეცდომაა", Toast.LENGTH_LONG).show()
                        }
                    })

            }

        }

    }


    private fun writeUser(userID: String, fName: String, lName: String, email: String) {

        var user: User = User(fName, lName, email)

        database.child(userID).setValue(user)

    }


}

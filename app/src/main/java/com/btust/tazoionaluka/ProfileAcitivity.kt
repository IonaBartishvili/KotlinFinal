package com.btust.tazoionaluka

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_profile_acitivity.*
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.text.TextUtils
import com.google.android.material.snackbar.Snackbar


class ProfileAcitivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_acitivity)


        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")

        //Customize the SnackBar for Later Use
        val snackBar = Snackbar.make(profileAcivityView, "", Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(Color.RED)


        changePswBtn.setOnClickListener {
            var user = auth.currentUser
            var newPassword = newPassword_input.text.toString()

            if (TextUtils.isEmpty(newPassword)){
                snackBar.setText("შეავსეთ ველი !")
                snackBar.show()
            }else if (newPassword.length < 6){
                snackBar.setText("სიმბოლოთა რაოდენობა უნდა აღემატებოდეს 5-ს !")
                snackBar.show()
            } else {

                user!!.updatePassword(newPassword).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this,
                            "პაროლის შეცვლა წარმატებით განხორციელდა",
                            Toast.LENGTH_LONG
                        ).show()
                        newPassword_input.setText("")
                    } else {
                        Toast.makeText(this, "შეცდომა", Toast.LENGTH_LONG).show()
                    }
                }
            }


        }

        logoutBtn.setOnClickListener {
            val logout = Intent(this, LoginActivity::class.java)
            startActivity(logout)
            finish()
        }


        uploadImageBtn.setOnClickListener {

            var imgUrl = imageUrl_Input.text.toString()

            if (TextUtils.isEmpty(imgUrl)){
                snackBar.setText("შეავსეთ ველი!")
                snackBar.show()
            } else {
                database.child(auth.currentUser?.uid!!)
                    .addValueEventListener(object : ValueEventListener {

                        override fun onCancelled(p0: DatabaseError) {
                        }

                        override fun onDataChange(snap: DataSnapshot) {
                            database.child(auth.currentUser?.uid!!).child("imgURL")
                                .setValue(imgUrl)
                        }

                    })

            }

        }

    }
}

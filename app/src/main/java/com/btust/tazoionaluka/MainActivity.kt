package com.btust.tazoionaluka

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var database: DatabaseReference
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")

        database.child(auth.currentUser?.uid!!)
            .addValueEventListener(object : ValueEventListener {

                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(snap: DataSnapshot) {

                    val userInfo: User = snap.getValue(User::class.java) ?: return
                    Toast.makeText(applicationContext, userInfo.email, Toast.LENGTH_LONG).show()

                }

            })



        logOutBtn.setOnClickListener {
            val gotoSignInPage = Intent(this, LoginActivity::class.java)
            startActivity(gotoSignInPage)
            finish()
        }

        changePswBtn.setOnClickListener {
            var user = auth.currentUser
            var newPassword = newPassword_input.text.toString()

            user!!.updatePassword(newPassword).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Toast.makeText(this, "პაროლის შეცვლა წარმატებით განხორციელდა", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this,  "შეცდომა", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

package com.btust.tazoionaluka

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var database: DatabaseReference
    lateinit var auth: FirebaseAuth

    private var array = arrayListOf<Any>(
        "Melbourne",
        "Cape Town",
        "Barcelona",
        "London"
    )

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

                    val userInfo: GetUser = snap.getValue(GetUser::class.java) ?: return
                    showEmail.text = userInfo.email
                    showFName.text = userInfo.fname
                    showLName.text = userInfo.lname

                    Picasso.get()
                        .load(userInfo.imgURL)
                        .into(imageView)

                }

            })


        profileSettings.setOnClickListener{
            var profile_Settings = Intent(this, ProfileAcitivity::class.java)
            startActivity(profile_Settings)
        }

        val adapter = ArrayAdapter(this, R.layout.row_ui, array)
        val listView: ListView = findViewById(R.id.listView)
        listView.adapter = adapter

        addTodoBtn.setOnClickListener {
            adapter.add(newTodo.text.toString())
        }



    }
}

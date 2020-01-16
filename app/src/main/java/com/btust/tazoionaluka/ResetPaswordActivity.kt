package com.btust.tazoionaluka

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_reset_pasword.*

class ResetPaswordActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pasword)

        auth = FirebaseAuth.getInstance()

        resetpasswBtn.setOnClickListener {
            val emailAddress = sendEmail.text.toString()
            auth.sendPasswordResetEmail(emailAddress).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Toast.makeText(this, "შეამოწმეთ მეილი", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "შეცდომა !", Toast.LENGTH_LONG).show()
                }
            }


        }

    }
}

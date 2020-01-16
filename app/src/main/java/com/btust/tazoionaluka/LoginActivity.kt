package com.btust.tazoionaluka

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        auth = FirebaseAuth.getInstance()

        loginBtn.setOnClickListener {
            var email = email_input.text.toString()
            var password = password_input.text.toString()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                Toast.makeText(this, "გთხოვთ შეავსოთ ყველა ველი", Toast.LENGTH_LONG).show()
            } else {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener { task ->
                    if(task.isSuccessful){


                        val gotoMainActivity = Intent(this, MainActivity::class.java)
                        startActivity(gotoMainActivity)
                        finish()
                    } else {
                        Toast.makeText(this, "შეცდომაა !", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }


        signUpBtn.setOnClickListener {
            var gotoSignUpActivity = Intent(this, SignUpActivity::class.java)
            startActivity(gotoSignUpActivity)
        }


        password_ressetBtn.setOnClickListener {
            var resetBtn = Intent(this, ResetPaswordActivity::class.java)
            startActivity(resetBtn)
        }




    }
}

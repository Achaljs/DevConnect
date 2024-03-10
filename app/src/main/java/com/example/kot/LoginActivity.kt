package com.example.kot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.kot.databinding.ActivityLoginBinding
import com.example.kot.databinding.ActivitySignUpBinding
import com.example.kot.utils.changeInProgress
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {


    val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.txtSignUp.setOnClickListener {
            startActivity(Intent(LoginActivity@this,signUp::class.java))
        }



    }

    fun login(view: View) {

       changeInProgress(true,binding.signInProgressBar,binding.btnSignIn)
        val username=binding.username.text.toString()
        val password=binding.edtSignInPassword.text.toString()
        if(validate(username,password)){

            Firebase.auth.signInWithEmailAndPassword(username,password).addOnCompleteListener{

                changeInProgress(false,binding.signInProgressBar,binding.btnSignIn)
                if(it.isSuccessful){
                    startActivity(Intent(LoginActivity@this,HomeActivity::class.java))
                }
                else{

                    Toast.makeText(this, it.exception?.localizedMessage,Toast.LENGTH_SHORT).show()
                }
            }

        }

    }


    fun validate(email:String,password:String): Boolean {


        if(email.equals("")){
            binding.username.error = "Enter the Username"
            return false
        }
        if(password.equals("")){
            binding.edtSignInPassword.error = "Enter the Password"
            return false
        }

        else{
            return true
        }

    }
}
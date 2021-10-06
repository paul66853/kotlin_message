package kotlin_message.com

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login) //頁面轉換

        login_button_login.setOnClickListener {
            val email = email_edittext_login.text.toString()
            val password = password_edittext_login.text.toString()

            Log.d("Login","Attempt login with email/pw: $email/***")

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    //不確定!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                .addOnCompleteListener{
                    if(!it.isSuccessful) return@addOnCompleteListener
                    //若成功
                    val result = it.result  // Kotlin says result is of type "AuthResult?"
                    if (result != null) {
                        val user = result.user   // Kotlin says user is of type "FirebaseUser?"
                        if (user != null) {
                            Log.d("Main", "Successfully sign in user with uid: ${user.uid}")
                        }
                    }
                }
                    //不確定TOO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                .addOnFailureListener {
                    if (it.message != null) {
                        Log.d("Main","Fail to sign in user: ${it.message}")
                    }
                }
        }

        back_to_register_textview.setOnClickListener {
            finish() //回原本的畫面
        }
    }
}
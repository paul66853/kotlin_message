package kotlin_message.com

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        register_button_register.setOnClickListener {
            performRegister() //將FUN寫到下面
        }

        already_have_account_text_view.setOnClickListener {
            Log.d("MainActivity","Try to show login activity")

            //以某種方式啟動登入活動
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

    }
    //FUN建立
    private fun performRegister(){
        val email = email_edittext_register.text.toString()
        val password = password_edittext_register.text.toString()

        //空值時不錯誤
        if(email.isEmpty() || password.isEmpty()) {
            //顯示空值時的提示
            Toast.makeText(this,"請輸入帳號和密碼",Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("MainActivity","Email is:"+email)
        Log.d("MainActivity","Password:$password")

        //Firebase使用者使用Email和Password創建帳戶
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(!it.isSuccessful) return@addOnCompleteListener

                //若成功
                val result = it.result  // Kotlin says result is of type "AuthResult?"
                if (result != null) {
                    val user = result.user   // Kotlin says user is of type "FirebaseUser?"
                    if (user != null) {
                        Log.d("Main", "Successfully created user with uid: ${user.uid}")
                    }
                }
            }
            .addOnFailureListener {
                if (it.message != null) {
                    Log.d("Main","Fail to create user: ${it.message}")
                }
            }
    }

}
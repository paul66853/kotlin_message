package kotlin_message.com

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        register_button_register.setOnClickListener {
            val email = email_edittext_register.text.toString()
            val password = password_edittext_register.text.toString()

            Log.d("MainActivity","Email is:"+email)
            Log.d("MainActivity","Password:$password")

            //Firebase使用者使用Email和Password創建帳戶

        }

        already_have_account_text_view.setOnClickListener {
            Log.d("MainActivity","Try to show login activity")

            //以某種方式啟動登入活動
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

    }
}
package kotlin_message.com

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        register_button_register.setOnClickListener {
            performRegister() //將FUN寫到下面
        }

        already_have_account_text_view.setOnClickListener {
            Log.d("RegisterActivity","Try to show login activity")

            //以某種方式啟動登入活動
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        selectphoto_button_register.setOnClickListener {
            Log.d("RegisterActivity","Try to show photo selector")

            val intent = Intent(Intent.ACTION_PICK) //這要GOOGLE!!!!!!!!
            intent.type = "image/*"
            startActivityForResult(intent,0)
        }
    }

    var selectedPhotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data !=null){
            //繼續並確認選擇的照片是甚麼
            Log.d("RegisterActivity","Photo was selected")

            selectedPhotoUri = data.data

            val  bitmap = MediaStore.Images.Media.getBitmap(contentResolver,selectedPhotoUri)

            selectphoto_imageview_register.setImageBitmap(bitmap)
            //被遮住了，使用下面這個
            selectphoto_button_register.alpha = 0f
            //UI顯示照片在按鈕中
//            val  bitmapDrawable = BitmapDrawable(bitmap)
//            selectphoto_button_register.setBackgroundDrawable(bitmapDrawable)
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

        Log.d("RegisterActivity","Email is:"+email)
        Log.d("RegisterActivity","Password:$password")

        //Firebase使用者使用Email和Password創建帳戶
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(!it.isSuccessful) return@addOnCompleteListener

                //若成功
                val result = it.result  // Kotlin says result is of type "AuthResult?"
                if (result != null) {
                    val user = result.user   // Kotlin says user is of type "FirebaseUser?"
                    if (user != null) {
                        Log.d("RegisterActivity", "Successfully created user with uid: ${user.uid}")

                        //將圖片上載到FIREBASE
                        uploadImageToFirebaseStorage()
                    }
                }
            }
            .addOnFailureListener {
                if (it.message != null) {
                    Log.d("RegisterActivity","Fail to create user: ${it.message}")
                    Toast.makeText(this,"註冊失敗 : ${it.message}",Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun uploadImageToFirebaseStorage(){
        if(selectedPhotoUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/image/$filename")

        //不清楚內部運作!!!!!!!!!!!!!
        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d("RegisterActivity","Successfully uploaded image: ${it.metadata?.path}")

                //顯示圖片下載網址
                ref.downloadUrl.addOnSuccessListener {
                    Log.d("RegisterActivity","File Location: $it")

                    saveUserToFirebaseDatabase(it.toString())
                }
            }
            .addOnFailureListener{
                //do some logging here
                Log.d("RegisterActivity","Faled to upload image to storage: ${it.message}")
            }
    }
    private fun  saveUserToFirebaseDatabase(profileImageUrl: String){
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(uid,username_edittext_register.text.toString(),profileImageUrl)

        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("ResisterActivity","Finally we saved the user to Firebase Database")

                //成功切換登入後
                val intent = Intent(this,LatestMessagesActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            .addOnFailureListener {
                Log.d("ResisterActivity","Faled to set value to database: ${it.message}")
            }
    }
}
class User(val uid: String, val username: String, val profileImageUrl: String){
    constructor() : this("","","")
}
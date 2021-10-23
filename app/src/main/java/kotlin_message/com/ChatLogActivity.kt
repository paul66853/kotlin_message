package kotlin_message.com

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.chat_from_row.view.*

class ChatLogActivity : AppCompatActivity() {

    companion object{
        val TAG = "ChatLog"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        supportActionBar?.title = "Chat Log"

        //更改點進使用者傳遞訊息介面上方BAR的名稱
       val username = intent.getStringExtra(NewMessageActivity.USER_KEY)
//        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)


            supportActionBar?.title = username

        //互相傳遞訊息頁面中的左方訊息列

        setupDummyData()

        send_button_chat_log.setOnClickListener{
            Log.d(TAG,"Attempt to send message....")
            performSendMessage()
        }
//        val adapter = GroupAdapter<GroupieViewHolder>()
//
//        adapter.add(ChatFromItem())
//        adapter.add(ChatToItem())
//        adapter.add(ChatFromItem())
//        adapter.add(ChatToItem())
//
//        recyclerview_chat_log.adapter = adapter
    }

    class ChatMessage(val text: String)

    private fun performSendMessage(){
        //這裡寫如何傳送訊息並上傳到FIREBASE上
        val text = edittext_chat_log.text.toString()


        val reference = FirebaseDatabase.getInstance().getReference("/messages").push() //在FIREBASE上的資料庫生成資料名為message的節點

        val chatMessage = ChatMessage(text)
        reference.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d(TAG,"儲存聊天紀錄: ${reference.key}")
            }
    }

    private fun setupDummyData(){
        val adapter = GroupAdapter<GroupieViewHolder>()

        adapter.add(ChatFromItem("from messaggggggggggggggee"))
        adapter.add(ChatToItem("to madfjlaksdjfl\nlsdjflksdfjal"))
        adapter.add(ChatFromItem("from messaggggggggggggggee"))
        adapter.add(ChatToItem("to madfjlaksdjfl\nlsdjflksdfjal"))
        adapter.add(ChatFromItem("from messaggggggggggggggee"))
        adapter.add(ChatToItem("to madfjlaksdjfl\nlsdjflksdfjal"))
        adapter.add(ChatFromItem("from messaggggggggggggggee"))
        adapter.add(ChatToItem("to madfjlaksdjfl\nlsdjflksdfjal"))
//        adapter.add(ChatFromItem())
//        adapter.add(ChatToItem())

        recyclerview_chat_log.adapter = adapter
    }
}
//左測的訊息欄
class ChatFromItem(val text: String): Item<GroupieViewHolder>(){
    override fun bind(p0: GroupieViewHolder, p1: Int) {
        p0.itemView.textview_from_row.text = text
    }
    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }
}
//右側的訊息欄
class ChatToItem(val text: String): Item<GroupieViewHolder>(){
    override fun bind(p0: GroupieViewHolder, p1: Int) {
        p0.itemView.textview_from_row.text = text
    }
    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }
}
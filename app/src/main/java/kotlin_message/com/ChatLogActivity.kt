package kotlin_message.com

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_chat_log.*

class ChatLogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        supportActionBar?.title = "Chat Log"

        //更改點進使用者傳遞訊息介面上方BAR的名稱
       val username = intent.getStringExtra(NewMessageActivity.USER_KEY)
//        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)


            supportActionBar?.title = username

        //互相傳遞訊息頁面中的左方訊息列
        val adapter = GroupAdapter<GroupieViewHolder>()

        adapter.add(ChatFromItem())
        adapter.add(ChatToItem())
        adapter.add(ChatFromItem())
        adapter.add(ChatToItem())

        recyclerview_chat_log.adapter = adapter
    }
}

class ChatFromItem: Item<GroupieViewHolder>(){
    override fun bind(p0: GroupieViewHolder, p1: Int) {

    }
    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }
}

class ChatToItem: Item<GroupieViewHolder>(){
    override fun bind(p0: GroupieViewHolder, p1: Int) {

    }
    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }
}
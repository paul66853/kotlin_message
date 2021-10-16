package kotlin_message.com

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_new_message.*
import kotlinx.android.synthetic.main.user_row_new_message.view.*


class NewMessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)

        //更改上方BAR的字
        supportActionBar?.title = "Select User"

        val adapter = GroupAdapter<GroupieViewHolder>()
//此為原先示範用格子，後面取得USERNAME後棄用
//        adapter.add(UserItem())
//        adapter.add(UserItem())
//        adapter.add(UserItem())
//
//        recycleview_newmessage.adapter = adapter //這是甚麼?

        fetchUsers()
    }

    //COMPANION 在做甚麼??????
    companion object{
        val USER_KEY = "USER_KEY"
    }

    private fun fetchUsers() {
        val ref = FirebaseDatabase.getInstance().getReference("/users")
        ref.addListenerForSingleValueEvent(object: ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                val adapter = GroupAdapter<GroupieViewHolder>()
                //沒跑出使用者資料和圖片位置!!!!!!!!!!!!
                snapshot.children.forEach{
                    Log.d("NewMessage",it.toString())
                    val user = it.getValue(User::class.java)
                    if(user != null){
                        adapter.add(UserItem(user))
                    }
                }

                //觸發ChatLogActivity
                adapter.setOnItemClickListener { item, view ->

                    val userItem = item as UserItem

                    val intent = Intent(view.context,ChatLogActivity::class.java)
                    intent.putExtra(USER_KEY,userItem.user.username)
//                    intent.putExtra(USER_KEY,userItem.user)

                    startActivity(intent)
                //直接切回訊息葉面，不回新增訊息葉面
                    finish()
                }

                recycleview_newmessage.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}

class UserItem(val user: User): Item<GroupieViewHolder>() {
    override fun bind(p0: GroupieViewHolder, p1: Int) {
        //等等會呼叫其他使用者的物件
        p0.itemView.username_textview_new_message.text = user.username
        //新訊息中讀取使用者頭像
        Picasso.get().load(user.profileImageUrl).into(p0.itemView.imageview_new_message)
    }

    override fun getLayout(): Int {
        //叫出頁面
        return R.layout.user_row_new_message
    }
}

//這是令人乏味的

//class CustomAdapter: RecyclerView.Adapter<ViewHolder>{
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        TODO("Not yet implemented")
//    }
//}
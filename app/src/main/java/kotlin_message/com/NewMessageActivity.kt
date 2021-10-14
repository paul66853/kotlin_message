package kotlin_message.com

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_new_message.*


class NewMessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)

        //更改上方BAR的字
        supportActionBar?.title = "Select User"

        val adapter = GroupAdapter<GroupieViewHolder>()

        recycleview_newmessage.adapter = adapter //這是甚麼?

    }
}

//這是令人乏味的

//class CustomAdapter: RecyclerView.Adapter<ViewHolder>{
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        TODO("Not yet implemented")
//    }
//}
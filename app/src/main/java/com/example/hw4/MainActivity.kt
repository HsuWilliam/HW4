package com.example.hw4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.hw4.databinding.ActivitySwipeRefreshBinding

class MainActivity : AppCompatActivity(),cnnlistAdapter.OnItemClickListener{
    var data = mutableListOf<VideoData>()
    //  val adapter : ArrayAdapter<String> by lazy{
    //    ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,titles)
    // }
    //val VideoList : List<VideoData> = listOf<VideoData>()
    // val adapter: VideoAdapter by lazy {
    //     VideoAdapter(this)
    //  }



    val swipeRefreshLayout by lazy{
        findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)
    }

    var binding: ActivitySwipeRefreshBinding?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe_refresh)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_swipe_refresh)

        //var linearLayout: LinearLayout = findViewById(R.id.linearlayout)
        // listAdapter = adapter

        swipeRefreshLayout.setOnRefreshListener {
            // titles.clear()
            loadList()
        }
        loadList()
    }

    //   override fun OnItemClick(position: Int) {
    //      val intent = Intent(this,PreviewActivity::class.java)
    //     intent.putExtra("title",data[position].title)

    // startActivity(intent)

    //}

    private fun loadList(){
        YoutubeSAX(object : Parser_Listener {
            override fun start() {
                swipeRefreshLayout.isRefreshing = true
            }

            override fun finish(videos: List<VideoData>) {
                for (video in videos) {
                    // linearLayout.addView(textView)
                    // data.add(video)
                    //data.add(video.thumbnail.toString())
                }
                //    adapter.notifyDataSetChanged()
                //adapter.Video = videos
                val cnnlistdata = cnnlistAdapter(this@MainActivity,this@MainActivity)

                cnnlistdata.Video = videos     //傳入資料

                var mrecyclerView = findViewById<RecyclerView>(R.id.recyclerView) as RecyclerView
                mrecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                mrecyclerView.adapter = cnnlistdata

                swipeRefreshLayout.isRefreshing = false

            }

        }).parseURL("https://www.youtube.com/feeds/videos.xml?channel_id=UCupvZG-5ko_eiXAupbDfxWw")
    }

}
package com.example.hw4

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hw4.databinding.YoutubeListItemBinding
import java.io.ByteArrayOutputStream

class cnnlistAdapter(val context: Context, private val listener : OnItemClickListener):
    RecyclerView.Adapter<cnnlistAdapter.ViewHolder>(){
    var Video : List<VideoData> = listOf<VideoData>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private var bmp: Bitmap?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cnnlistAdapter.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        //val v = LayoutInflater.from(parent.context).inflate(R.layout.youtube_list_item, parent, false)
        val binding = DataBindingUtil.inflate<YoutubeListItemBinding>(inflater,R.layout.youtube_list_item,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return Video.size
    }

    override fun onBindViewHolder(holder: cnnlistAdapter.ViewHolder, position: Int) {
        val bs = ByteArrayOutputStream()
        holder.binding.videoData = Video[position]
        holder.binding.videoTitle.text = Video[position].title
        holder.binding.imgThumbnail.setImageBitmap(Video[position].thumbnail)
        holder.binding.root.setOnClickListener(){
            var intent = Intent(context,PreviewActivity::class.java)
            bmp = Video[position].thumbnail
            bmp?.compress(Bitmap.CompressFormat.JPEG,100,bs)
            intent.putExtra("title",Video[position].title)
            intent.putExtra("description",Video[position].description)
            intent.putExtra("videoId",Video[position].videoId)
            intent.putExtra("image",bs.toByteArray())
            context.startActivity(intent)
        }

        //holder?.bind(Video[position])


    }
    inner class ViewHolder(val binding:YoutubeListItemBinding): RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        //    val videoTitle: TextView = itemView.findViewById(R.id.video_title)
        //   val img_thumbnail: ImageView = itemView.findViewById((R.id.img_thumbnail))


        //   fun bind(datas: VideoData){
        //     videoTitle.text = datas.title
        //    img_thumbnail.setImageBitmap(datas.thumbnail)
        // }
        init{
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position  = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.OnItemClick(position)
            }

        }
    }
    interface OnItemClickListener{
        fun OnItemClick(position: Int){

        }
    }
}
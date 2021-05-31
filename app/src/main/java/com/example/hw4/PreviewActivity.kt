package com.example.hw4

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Bundle
import android.os.PersistableBundle
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
//import com.example.hw4.databinding.ActivityPreviewBinding
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

import java.util.regex.Pattern

class PreviewActivity : YouTubeBaseActivity() {
    private var title: String?= null
    private var description:String?=null
    private var image: Bitmap?=null
    private var videoId: String?=null




    //private lateinit var binding: ActivityPreviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = layoutInflater.inflate(R.layout.activity_preview, null) as ConstraintLayout
        setContentView(layout)

        val playerView = YouTubePlayerView(this)
        playerView.layoutParams = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        layout.addView(playerView)
        //setContentView(R.layout.activity_preview)
      //  binding = DataBindingUtil.setContentView(this,R.layout.activity_preview)
        val intent = getIntent()
      //  title = intent.getStringExtra("title")
      //  description = intent.getStringExtra("description")
        videoId = intent.getStringExtra("videoId")
        //val youtubeplayerView = findViewById<YouTubePlayerView>(R.id.youtubeplayer)
        playerView.initialize(getString(R.string.api_key) ,object : YouTubePlayer.OnInitializedListener{
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                p1?.cueVideo(videoId)

            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {

            }



        })

      //  val byteArray = getIntent().getByteArrayExtra("image")
      //  image = intent.getParcelableExtra("image")
      //  val textView = findViewById<TextView>(R.id.textView_title)
      //  binding.title= title
       // val description_str = findViewById<TextView>(R.id.textView_description)
      //  binding.description = description
      //  binding.videoId = videoId
      //  if (byteArray != null) {
      //      image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
       // }
       // binding.thumbnail = image
    }

    override fun onSaveInstanceState(p0: Bundle) {
        super.onSaveInstanceState(p0)
        //p0.putBoolean("")
    }

    private val playbackEventListener = object: YouTubePlayer.PlaybackEventListener {
        override fun onSeekTo(p0: Int) {
        }

        override fun onBuffering(p0: Boolean) {
        }

        override fun onPlaying() {
            Toast.makeText(this@PreviewActivity, "Good, video is playing ok", Toast.LENGTH_SHORT).show()
        }

        override fun onStopped() {
            Toast.makeText(this@PreviewActivity, "Video has stopped", Toast.LENGTH_SHORT).show()
        }

        override fun onPaused() {
            Toast.makeText(this@PreviewActivity, "Video has paused", Toast.LENGTH_SHORT).show()
        }
    }

    private val playerStateChangeListener = object: YouTubePlayer.PlayerStateChangeListener {
        override fun onAdStarted() {
            Toast.makeText(this@PreviewActivity, "Click Ad now, make the video creator rich!", Toast.LENGTH_SHORT).show()
        }

        override fun onLoading() {
        }

        override fun onVideoStarted() {
            Toast.makeText(this@PreviewActivity, "Video has started", Toast.LENGTH_SHORT).show()
        }

        override fun onLoaded(p0: String?) {
        }

        override fun onVideoEnded() {
            Toast.makeText(this@PreviewActivity, "Congratulations! You've completed another video.", Toast.LENGTH_SHORT).show()
        }

        override fun onError(p0: YouTubePlayer.ErrorReason?) {
        }
    }
}
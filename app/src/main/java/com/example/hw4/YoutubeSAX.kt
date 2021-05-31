package com.example.hw4

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler
import java.net.URL
import javax.xml.parsers.SAXParserFactory

class YoutubeSAX(var listener: Parser_Listener): DefaultHandler() {
    private val factory = SAXParserFactory.newInstance()
    private val parser = factory.newSAXParser()

    private var entryFound = false
    private var titleFound = false
    private var imageFound = false
    private var thumbnail : Bitmap? = null
    private var descriptionFound = false
    private var videoIdFound = false
    private var element = ""
    private var videoTitle = ""
    private var thumbnail_url =""
    private var description = ""
    private var videoId = ""
    private var data = mutableListOf<VideoData>()

    fun parseURL(url:String){
        listener.start()
        GlobalScope.launch {
            try{
                var inputStream = URL(url).openStream()
                parser.parse(inputStream, this@YoutubeSAX)
                withContext(Dispatchers.Main){
                    listener.finish(data)
                }
            }catch (e: Throwable){
                e.printStackTrace()
            }
        }





    }

    override fun characters(ch: CharArray?, start: Int, length: Int) {
        super.characters(ch, start, length)
        ch?.let {
            element += String(it,start,length)
        }
    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {
        super.endElement(uri, localName, qName)
        if(entryFound){
            if(titleFound){
                titleFound = false
                videoTitle = element
                Log.i("Title",videoTitle)
            }else if(imageFound){
                //val url = element
                //Log.i("URL",url)
                val inputStream = URL(thumbnail_url).openStream()
                thumbnail = BitmapFactory.decodeStream(inputStream)
                imageFound = false
            }else if(descriptionFound){
                descriptionFound = false
                description = element
            }else if(videoIdFound){
                videoIdFound = false
                videoId = element
            }
        }
        if(localName == "entry"){
            entryFound = false
            data.add(VideoData(videoTitle,thumbnail,description,videoId))
        }
    }

    override fun startElement(
        uri: String?,
        localName: String?,
        qName: String?,
        attributes: Attributes?
    ) {
        super.startElement(uri, localName, qName, attributes)

        if(localName == "entry"){
            entryFound = true
        }
        if(entryFound){
            if(localName == "title") {
                titleFound = true
            }else if(localName == "thumbnail" ){
                thumbnail_url = attributes?.getValue("url").toString()
                imageFound = true
            }else if(localName == "description"){
                descriptionFound = true
            }else if(localName == "videoId" ){
                videoIdFound = true
            }
        }
        element = ""
    }
}
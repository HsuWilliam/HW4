package com.example.hw4

interface Parser_Listener {
    fun start()

    fun finish(video: List<VideoData>) {
    }
}
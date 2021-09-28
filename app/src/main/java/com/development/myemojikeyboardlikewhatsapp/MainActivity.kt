package com.development.myemojikeyboardlikewhatsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.development.myemojikeyboardlikewhatsapp.databinding.ActivityMainBinding
import com.vanniktech.emoji.EmojiManager
import com.vanniktech.emoji.EmojiPopup
import com.vanniktech.emoji.facebook.FacebookEmojiProvider
import com.vanniktech.emoji.google.GoogleEmojiProvider
import com.vanniktech.emoji.twitter.TwitterEmojiProvider

/*Reference : https://github.com/vanniktech/Emoji*/

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val emojiPopup by lazy {EmojiPopup.Builder.fromRootView(binding.imgSmile).build(binding.etWriteSomething)  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        EmojiManager.install(GoogleEmojiProvider())
        /*EmojiManager.install(FacebookEmojiProvider())
        EmojiManager.install(TwitterEmojiProvider())*/

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.fragment = this

        binding.imgSmile.setOnClickListener {
            emojiPopup.toggle()
        }

        binding.etWriteSomething.setOnClickListener{
            emojiPopup.dismiss()
        }

        binding.imgSend.setOnClickListener {
            binding.tvShow.text = binding.etWriteSomething.text?.trim()
        }
    }
}
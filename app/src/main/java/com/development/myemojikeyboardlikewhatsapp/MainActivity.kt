package com.development.myemojikeyboardlikewhatsapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.development.myemojikeyboardlikewhatsapp.databinding.ActivityMainBinding
import com.giphy.sdk.analytics.models.enums.AttributeKey
import com.giphy.sdk.core.models.Media
import com.giphy.sdk.ui.GPHContentType
import com.giphy.sdk.ui.GPHSettings
import com.giphy.sdk.ui.Giphy
import com.giphy.sdk.ui.views.GiphyDialogFragment
import com.vanniktech.emoji.EmojiManager
import com.vanniktech.emoji.EmojiPopup
import com.vanniktech.emoji.facebook.FacebookEmojiProvider
import com.vanniktech.emoji.google.GoogleEmojiProvider
import com.vanniktech.emoji.twitter.TwitterEmojiProvider

/*Todo Reference : https://github.com/vanniktech/Emoji  (EMOJI)*/
/*Todo Reference: https://developers.giphy.com/docs/sdk#android  (GIF)*/

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val emojiPopup by lazy {EmojiPopup.Builder.fromRootView(binding.imgSmile).build(binding.etWriteSomething)  }

    private var gifUrl=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        EmojiManager.install(GoogleEmojiProvider())
        /*EmojiManager.install(FacebookEmojiProvider())
        EmojiManager.install(TwitterEmojiProvider())*/

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.fragment = this
        Giphy.configure(this, getString(R.string.giphyAPIKey))

        binding.imgSmile.setOnClickListener {
            emojiPopup.toggle()
        }

        binding.etWriteSomething.setOnClickListener{
            emojiPopup.dismiss()
        }

        binding.imgGif.setOnClickListener {
            showGifDialog("post", 0)
        }

        binding.imgSend.setOnClickListener {
            binding.tvShow.text = binding.etWriteSomething.text?.trim()
        }
    }

    private fun showGifDialog(type:String?, position:Int){
        val settings =  GPHSettings()
        val dialog = GiphyDialogFragment.newInstance(settings)
        dialog.gifSelectionListener = object : GiphyDialogFragment.GifSelectionListener{
            override fun didSearchTerm(term: String) {

            }
            override fun onDismissed(selectedContentType: GPHContentType) {

            }
            override fun onGifSelected(
                media: Media, searchTerm: String?, selectedContentType: GPHContentType
            ) {
                gifUrl = media.images.original?.gifUrl?:""
                when(type){
                    "post"->{
                        Glide.with(this@MainActivity)
                            .asGif()
                            .load(media.images.original?.gifUrl?:"")
                            .apply(RequestOptions().placeholder(R.mipmap.ic_launcher))
                            .into(binding.imgShow)

                        binding.imgShow.visibility = View.VISIBLE
                    }
                }

            }
        }
        dialog.show(this.supportFragmentManager, "giphy_dialog");
    }
}
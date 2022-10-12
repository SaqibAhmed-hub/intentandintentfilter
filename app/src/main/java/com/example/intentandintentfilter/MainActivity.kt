package com.example.intentandintentfilter

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        * sending the sample text to Other App
        * */
        val msg = "This is a sample message"
        btn_send.setOnClickListener {
            Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, msg)
                startActivity(this)
            }
        }

        /*
        * You Need the Call Permission to work.
        * ACTION_DIAL : will take you to dail pad.
        * ACTION_CALL : will directly call the number.
        * */
        btn_call.setOnClickListener {
            Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:9738307490")
                startActivity(this)
            }
        }

        btn_image.setOnClickListener {
            Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
                type = "image/*"
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                startActivityForResult(this, GALLERY)
            }
        }

        btn_note.setOnClickListener {

        }


        /*
        * This is the way to Handle the Intent.
        * */
        if (intent?.action == Intent.ACTION_SEND) {
            handleIntent(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY && resultCode == Activity.RESULT_OK ){
            val muri = data?.data
            sendToOtherApp(muri)
        }
    }

    private fun sendToOtherApp(muri: Uri?) {
        Intent().apply {
            action = Intent.ACTION_SEND
            type = "images/*"
            putExtra(Intent.EXTRA_STREAM,muri)
        }.also {
            startActivity(Intent.createChooser(it,"Share the Image with"))
        }
    }

    private fun handleIntent(intent: Intent) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
            txt_action.text = it
        }
    }

    companion object {
        private const val GALLERY = 101
    }
}
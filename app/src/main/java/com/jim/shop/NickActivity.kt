package com.jim.shop

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_nick.*

class NickActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //=====================================================↓
        setContentView(R.layout.activity_nick)
        //=====================================================↑
        done.setOnClickListener {
            setNickname(nick.text.toString())
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}
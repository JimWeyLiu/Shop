package com.jim.shop

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_nick.*

class NickActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nick)
        done.setOnClickListener {
            getSharedPreferences("shop",Context.MODE_PRIVATE)//當案名稱
                .edit()//呼叫方法
                .putString("NICKNAME",nick.text.toString())//暱稱放進來
                .apply()//儲存
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}
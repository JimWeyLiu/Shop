package com.jim.shop

import android.app.Activity
import android.content.Context

fun Activity.setNickname(nickname:String) {
    getSharedPreferences("shop", Context.MODE_PRIVATE)//檔當案名稱
        .edit()//呼叫方法
        .putString("NICKNAME",nickname)//暱稱放進來
        .apply()//儲存
}

fun  Activity.getNickname(): String? {
    return getSharedPreferences("shop", Context.MODE_PRIVATE).getString("NICKNAME","")
}
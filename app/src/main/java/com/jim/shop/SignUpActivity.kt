package com.jim.shop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        signup.setOnClickListener {
            val sEmail:String = email.text.toString()
            val sPassword:String = password.text.toString()
            FirebaseAuth
                .getInstance()//取得Firebase的物件
                .createUserWithEmailAndPassword(sEmail,sPassword)// 創建帳密(mail,password)
            Log.d("onCreate: ",sEmail+sPassword)
        }
    }
}
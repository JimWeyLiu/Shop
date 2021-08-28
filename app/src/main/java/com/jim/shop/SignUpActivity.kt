package com.jim.shop

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
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
                //非同步確認方式↓
                .addOnCompleteListener {
                    if (it.isSuccessful()) {
                        AlertDialog.Builder(this)
                            .setTitle("Sign Up")
                            .setMessage("Account created")
                            .setPositiveButton("ok") { dialog,which ->
                                setResult(Activity.RESULT_OK)
                                finish()//按下ok後結束就回第一頁
                            }.show()
                    } else {
                        //已創建就顯示訊息
                        AlertDialog.Builder(this)
                            .setTitle("Sign Up")
                            .setMessage(it.exception?.message)
                            .setPositiveButton("ok",null)
                            .show()
                    }
                }
        }
    }
}
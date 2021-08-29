package com.jim.shop

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jim.shop.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {



    private val RC_NICKNAME = 210
    private val RC_SIGNUP = 200 //
    var signup = false // 預設未註冊
    val auth=FirebaseAuth.getInstance()//頃聽器

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        /*if (!signup) { //如果未註冊開啟SignUpActivity畫面
            val intent = Intent(this,SignUpActivity::class.java)
            startActivityForResult(intent,RC_SIGNUP) // 使用startActivityForResult
        }*/
        auth.addAuthStateListener { auth->
            authChanged(auth)
        }

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
    //=====================================================↓
    override fun onResume() {
        super.onResume()
//      nickname.text = getNickname()
        if (auth.currentUser != null) {
            FirebaseDatabase.getInstance()
                .getReference("users")
                .child(auth.currentUser!!.uid)
                .child("nickname")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.value !=null){
                            Log.d("onDataChange: ",dataSnapshot.toString())
                        nickname.text = dataSnapshot.value as String
                        }
                    }
                })
            Log.d("onResume: ",auth.currentUser.toString())
        }
    }
    //=====================================================↑

    private fun authChanged(auth: FirebaseAuth) {
        if (auth.currentUser==null){//取得目前使用者
            val intent = Intent(this,SignUpActivity::class.java)
            startActivityForResult(intent,RC_SIGNUP) // 使用startActivityForResult
        } else {
            Log.d("MainActivity",  "authChanged: ${auth.currentUser?.uid}")
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) { //覆寫onActivityResult
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGNUP) {
            if (resultCode == Activity.RESULT_OK){
                val intent = Intent(this,NicknameActivity::class.java)
                startActivityForResult(intent,RC_NICKNAME)
            }
            if (requestCode == RC_NICKNAME) {//輸入完成後的判斷

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
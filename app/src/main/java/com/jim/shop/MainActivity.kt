package com.jim.shop

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import android.view.Menu
import android.view.MenuItem
import com.jim.shop.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //=====================================================↓
    private val RC_SIGNUP = 200 //
    var signup = false // 預設未註冊
    //=====================================================↑
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //=====================================================↓
        setSupportActionBar(binding.toolbar)
        if (!signup) { //如果未註冊開啟SignUpActivity畫面
            val intent = Intent(this,SignUpActivity::class.java)
            startActivityForResult(intent,RC_SIGNUP) // 使用startActivityForResult
        }
        //=====================================================↑
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) { //覆寫onActivityResult
        super.onActivityResult(requestCode, resultCode, data)

    }
    //=====================================================↓
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    //=====================================================↑
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
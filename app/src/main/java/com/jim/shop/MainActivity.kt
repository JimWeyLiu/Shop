package com.jim.shop

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jim.shop.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.row_function.view.*
import java.text.FieldPosition

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    private val RC_NICKNAME = 210
    private val RC_SIGNUP = 200 //
    var signup = false // 預設未註冊
    val auth=FirebaseAuth.getInstance()//頃聽器

    val functions = listOf<String>("Camera", //可在此處增加項目
        "Invite friend",
        "Parking",
        "Download coupons",
        "New",
        "Maps")

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

        //Spinner
        val colors = arrayOf("Red","Green","Blue")
        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,colors)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinner.adapter=adapter
        spinner.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) { //選完之後執行
                Log.d("MainActivity", "onItemSelected: ${colors[position]}")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }
        }

        //RecyclerView 清單功能表
        recycler.layoutManager = LinearLayoutManager(this) //清單式的一個編排頁面
        recycler.setHasFixedSize(true) //設定固定大小=真
        recycler.adapter = FunctionAdapter()
    }
    inner class FunctionAdapter() : RecyclerView.Adapter<FunctionHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FunctionHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_function,parent,false)
            val holder = FunctionHolder(view)
            return holder
        }
        override fun onBindViewHolder(holder: FunctionHolder, position: Int) {
            holder.nameText.text = functions.get(position)
            holder.itemView.setOnClickListener { view ->
                functionClicked(holder,position)
            }
        }

        override fun getItemCount(): Int { //它在問說這一個裡面到底有幾筆資料
            return functions.size
        }
    }
    private fun functionClicked(holder: MainActivity.FunctionHolder, position: Int) {
        Log.d(TAG, "functionClicked: $position")
        when(position){
            1 -> startActivity(Intent(this,ContactActivity::class.java))//按下後跳頁 例如：收到1開啟此頁面
            // ==========================================↓
            2 -> startActivity(Intent(this,ParkingActivity::class.java))
            // ==========================================↑

        }
    }

    class FunctionHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nameText :TextView = view.name

    }
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
package com.example.headsupa_room

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var edn: EditText
    lateinit var edt1:EditText
    lateinit var edt2:EditText
    lateinit var edt3:EditText
    lateinit var rv: RecyclerView
    lateinit var add:Button
    lateinit var db:CelebDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        add.setOnClickListener{
            if(edn.text.isNotEmpty()&&edt1.text.isNotEmpty()&&edt2.text.isNotEmpty()&&edt3.text.isNotEmpty()){
                CoroutineScope(Dispatchers.IO).launch {
                    var st = db.addeditCeleb(
                        Celeb(
                            0,
                            edn.text.toString(),
                            edt1.text.toString(),
                            edt2.text.toString(),
                            edt3.text.toString()
                        )
                    )


                    runOnUiThread{setuprvdata()}
                }
                Toast.makeText(this@MainActivity, "note saved", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun init() {
        db= CelebDB.getInstance(this).CelebDao()
        edn = findViewById(R.id.edcelep)
        edt1 = findViewById(R.id.edtb1)
        edt2 = findViewById(R.id.edtb2)
        edt3 = findViewById(R.id.edtb3)
        rv = findViewById(R.id.rvm)
        add = findViewById(R.id.add)
        setuprvdata()
    }
    fun setuprvdata() {
        var list=listOf<Celeb>()
        CoroutineScope(Dispatchers.IO).launch {
            list = db.getall()
            runOnUiThread {
                rv.adapter = RVAdapter(list as ArrayList<Celeb>, this@MainActivity)
                rv.layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }
    }
    /////////////////////menu////////////////////////////


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        val item1 = menu!!.getItem(0)
        item1.setTitle("switch to game")


        return super.onPrepareOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.m1 -> {
                intent= Intent(this, Game::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

package com.example.headsupa_room

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.Surface
import android.widget.*
import androidx.core.view.isVisible
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Game : AppCompatActivity() {
    lateinit var main: LinearLayout
    lateinit var ques: LinearLayout
    lateinit var tvTime: TextView
    lateinit var tvName: TextView
    lateinit var tab1: TextView
    lateinit var tab2: TextView
    lateinit var tab3: TextView
    lateinit var tvMain: TextView
    lateinit var btStart: Button
    lateinit var db:CelebDao
    var gameActive = false
    var cel = listOf<Celeb>()
    var celeb = 0
    lateinit var time : CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        init()
        btStart.setOnClickListener {
            if(cel.isEmpty()){
                getcel()
                newgame()
            }else{
                newgame()
            }
        }


    }

    private fun getcel() {
        CoroutineScope(Dispatchers.IO).launch {
            cel = db.getall()
        }

    }


    fun init(){
        db= CelebDB.getInstance(this).CelebDao()
        main = findViewById(R.id.main)
        ques = findViewById(R.id.quis)
        tvTime = findViewById(R.id.tvTime)
        tvName = findViewById(R.id.tvName)
        tab1 = findViewById(R.id.tvTaboo1)
        tab2 = findViewById(R.id.tvTaboo2)
        tab3 = findViewById(R.id.tvTaboo3)
        tvMain = findViewById(R.id.tvMain)
        btStart = findViewById(R.id.btStart)

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val rotation = windowManager.defaultDisplay.rotation
        if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) {
            if (gameActive) {
                time.cancel();
                updateStatus(false)
            } else {
                updateStatus(false)
            }
        } else {
            if (gameActive) {
                newCelebrity(celeb)
                celeb++
                updateStatus(true)
                time.start()
            } else {

                updateStatus(false)
            }
        }
    }

    private fun newgame() {
        if (!gameActive) {
            gameActive = true
            tvMain.text = "Please Rotate Device"
            btStart.isVisible = false
            val rotation = windowManager.defaultDisplay.rotation
            if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) {
                updateStatus(false)
            } else {
                newCelebrity(celeb)
                celeb++
                updateStatus(true)
                time = object : CountDownTimer(60000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        tvTime.text = "Time: ${millisUntilFinished / 1000}"

                    }

                    override fun onFinish() {
                        gameActive = false
                        tvTime.text = "Time: --"
                        tvMain.text = "Heads Up!"
                        btStart.isVisible = true
                        updateStatus(false)
                    }
                }
            }

        }
    }

    private fun newCelebrity(id: Int) {
        if (id < cel.size) {
            tvName.text = cel[id].name
            tab1.text = cel[id].taboo1
            tab2.text = cel[id].taboo2
            tab3.text = cel[id].taboo3
        }
    }


    private fun updateStatus(showCelebrity: Boolean) {
        if (showCelebrity) {
            ques.isVisible = true
            main.isVisible = false
        } else {
            ques.isVisible = false
            main.isVisible = true
        }
    }

    /////////// data guardian from the rotation monster
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        var cel = arrayListOf<Celeb>()
        outState.putInt("celeb", celeb)
        outState.putBoolean("gameactive",gameActive)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        celeb= savedInstanceState.getInt("celeb")
        gameActive=savedInstanceState.getBoolean("gameactive")
        CoroutineScope(Dispatchers.IO).launch {
            cel = db.getall()
        }
        newCelebrity(celeb)

    }

    /////////////////////menu////////////////////////////


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        val item1 = menu!!.getItem(0)
        item1.setTitle("switch to edit db")


        return super.onPrepareOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.m1 -> {
                intent= Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
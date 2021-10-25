package com.example.headsupa_room

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RVAdapter(private val rv: ArrayList<Celeb>, val cont: Context): RecyclerView.Adapter<RVAdapter.ItemViewHolder>()  {
    lateinit var ctit:TextView
    lateinit var tb1:TextView
    lateinit var tb2:TextView
    lateinit var tb3:TextView
    lateinit var rvlist:CardView
    lateinit var ed:ImageButton
    lateinit var del:ImageButton
    val db= CelebDB.getInstance(cont).CelebDao()
    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rvlist,parent,false )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.apply {
            uiin(holder)
            ctit.text = rv[position].name
            tb1.text= rv[position].taboo1
            tb2.text=rv[position].taboo2
            tb3.text=rv[position].taboo3
            ed.setOnClickListener{
                alert(rv[position])
            }
            del.setOnClickListener{
                CoroutineScope(Dispatchers.IO).launch {
                    db.deleteCeleb(rv[position])
                }
                    if (cont is MainActivity)
                        cont.setuprvdata()

            }



        }
    }
    fun uiin(holder: ItemViewHolder){
        holder.itemView.apply {
            rvlist= findViewById<CardView>(R.id.rvlisting)
            ctit= findViewById<TextView>(R.id.cardtitle)
            tb1 = findViewById<TextView>(R.id.tb1)
            tb2 = findViewById<TextView>(R.id.tb2)
            tb3 = findViewById<TextView>(R.id.tb3)
            ed=findViewById<ImageButton>(R.id.edbut)
            del=findViewById<ImageButton>(R.id.delbut)
        }
    }

    fun alert(celeb: Celeb){
        var c=celeb
        val d = AlertDialog.Builder(cont)
        lateinit var input: EditText
        lateinit var tb1: EditText
        lateinit var tb2: EditText
        lateinit var tb3: EditText
        lateinit var vv:View

        d.setCancelable(false)
        d.setPositiveButton("Change") { _, _ ->
            c.name = input.text.toString()
            c.taboo1=tb1.text.toString()
            c.taboo2=tb2.text.toString()
            c.taboo3=tb3.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                db.addeditCeleb(c)
            }
            if(cont is MainActivity)
                cont.setuprvdata()
        }
            .setNegativeButton("Cancel") { d, _ -> d.cancel() }


        val alert = d.create()
        alert.setTitle("Edit celebrity")
        if(cont is MainActivity)
        vv=cont.layoutInflater.inflate(R.layout.alert,null)
        alert.setView(vv)
        input= vv.findViewById(R.id.edn)
        tb1=vv.findViewById(R.id.edatb1)
        tb2=vv.findViewById(R.id.edatb2)
        tb3=vv.findViewById(R.id.edatb3)
        input.setText(celeb.name)
        tb1.setText(celeb.taboo1)
        tb2.setText(celeb.taboo2)
        tb3.setText(celeb.taboo3)

        alert.show()

    }

    override fun getItemCount() = rv.size
}
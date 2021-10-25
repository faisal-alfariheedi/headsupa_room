package com.example.headsupa_room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Celeb")
class Celeb(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id:Int,
            @ColumnInfo(name = "Name") var name:String,
            @ColumnInfo(name = "taboo1") var taboo1:String,
            @ColumnInfo(name = "taboo2") var taboo2:String,
            @ColumnInfo(name = "taboo3") var taboo3:String
            )

package com.example.headsupa_room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Celeb::class],version = 1,exportSchema = false)
abstract class CelebDB: RoomDatabase() {

    companion object {
        @Volatile
        var instance:CelebDB?=null
        fun getInstance(cont: Context):CelebDB{
            return instance?:synchronized(this){
                instance?:buildDatabase(cont).also{instance=it}
            }
        }
        fun buildDatabase(cont: Context):CelebDB{
            return Room.databaseBuilder(cont,CelebDB::class.java,"Celeb").build()
        }
    }
    abstract fun CelebDao():CelebDao

}


//@Database(entities = [Student::class],version = 1,exportSchema = false)
//abstract class StudentDatabase:RoomDatabase() {
//
//    companion object{
//        var instance:StudentDatabase?=null;
//        fun getInstance(ctx: Context):StudentDatabase
//        {
//            if(instance!=null)
//            {
//                return  instance as StudentDatabase;
//            }
//            instance= Room.databaseBuilder(ctx,StudentDatabase::class.java,"somename").run { allowMainThreadQueries() }.build();
//            return instance as StudentDatabase;
//        }
//    }
//    abstract fun StudentDao():StudentDao;
//}

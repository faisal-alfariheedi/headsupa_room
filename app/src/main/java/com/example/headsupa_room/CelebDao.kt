package com.example.headsupa_room

import androidx.room.*

@Dao
interface CelebDao {
    @Query("SELECT * from Celeb")
    fun getall():List<Celeb>

    @Insert(onConflict= OnConflictStrategy.REPLACE)
    fun addeditCeleb(celeb: Celeb)

    @Delete
    fun deleteCeleb(celeb: Celeb)
}
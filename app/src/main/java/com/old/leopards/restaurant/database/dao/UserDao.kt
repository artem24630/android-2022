package com.old.leopards.restaurant.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.old.leopards.restaurant.database.entities.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Query("SELECT * FROM users ORDER BY id ASC")
    fun getAllUsers(): LiveData<List<User>>
}
package com.riad.content_provider.data.db.dao

import android.database.Cursor
import androidx.room.*
import com.riad.content_provider.data.db.entity.UserEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface UserDao {

    @Insert
    fun insertUser(user: UserEntity): Completable

    @Query("Select * from users")
    fun gelAllUsers(): Flowable<List<UserEntity>>

    @Query("Select * from users")
    fun gelAllUsersCursor(): Cursor

    @Query("SELECT * FROM Users WHERE id = :id")
    fun getUser(id: Int): Flowable<UserEntity>

    @Query("SELECT * FROM Users WHERE id = :id")
    fun getUserCursor(id: Int): Cursor

    @Query("DELETE FROM Users WHERE id = :id")
    fun deleteUserById(id: Int): Completable

    @Update
    fun updateUser(user: UserEntity): Completable

    @Query("UPDATE Users SET username = :username WHERE id =:id")
    fun updateUserName(username: String?, id: Int)

    @Query("UPDATE Users SET username = :username WHERE username =:oldname")
    fun updateUserByName(username: String?, oldname: String): Completable

    @Delete
    fun deleteUser(user: UserEntity): Completable

}
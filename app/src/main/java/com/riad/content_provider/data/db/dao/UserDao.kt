package com.riad.content_provider.data.db.dao

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

    @Query("SELECT * FROM Users WHERE id = :id")
    fun getUser(id: Int): Flowable<UserEntity>

    @Query("DELETE FROM Users WHERE id = :id")
    fun deleteUserById(id: Int): Completable

    @Update
    fun updateUser(user: UserEntity): Completable

    @Delete
    fun deleteUser(user: UserEntity): Completable

}
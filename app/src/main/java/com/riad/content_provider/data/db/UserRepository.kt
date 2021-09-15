package com.riad.content_provider.data.db

import android.content.Context
import android.os.AsyncTask
import androidx.room.Query
import com.riad.content_provider.App
import com.riad.content_provider.data.db.dao.UserDao
import com.riad.content_provider.data.db.entity.UserEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class UserRepository() {

    var db: UserDao = AppDatabase.getInstance(App.instance)?.userDao()!!


    //Fetch All the Users
    fun getAllUsers(): Flowable<List<UserEntity>> {
        return db.gelAllUsers()
    }

    // Insert new user
    fun insertUser(user: UserEntity): Completable {
        return db.insertUser(user)
    }

    fun getUser(id: Int): Flowable<UserEntity> {
        return db.getUser(id)
    }

    fun deleteUserById(id: Int): Completable {
        return db.deleteUserById(id)
    }

    // update user
    fun updateUser(users: UserEntity): Completable {
        return db.updateUser(users)
    }

    // Delete user
    fun deleteUser(users: UserEntity): Completable {
        return db.deleteUser(users)
    }
}
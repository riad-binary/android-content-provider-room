package com.riad.content_provider.views

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.gson.Gson
import com.riad.content_provider.R
import com.riad.content_provider.data.db.BookRepository
import com.riad.content_provider.data.db.UserRepository
import com.riad.content_provider.data.db.entity.BookEntity
import com.riad.content_provider.data.db.entity.UserEntity
import com.riad.content_provider.data.provider.DataProvider
import com.riad.content_provider.data.provider.PrivderConstants
import com.riad.content_provider.utils.Constants
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Handler(Looper.getMainLooper()).postDelayed({
            getAllData()
        }, 3000)



        Single.fromCallable {
            // method that run in background
            getAllUsersFromContentProvider()
//            getUserFromContentProvider(3)

        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

//        insertToBook()

//        var disposableUser1 = userRepository.insertUser(UserEntity(userName = "riad3"))
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    Log.e("rrr", "insertUser complete:  ")
//                },
//                {
//                    Log.e("rrr", "insertUser error:" + it.message )
//                }
//            )


//        var disposableBook1 = bookRepository.insertBook(BookEntity(name = "game of thrones", type= "fantasy"))
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    Log.e("rrr", "insertBook complete:  ")
//                },
//                {
//                    Log.e("rrr", "insertBook error:" + it.message)
//                }
//            )

//        var disposableBook2 = bookRepository.getAllBooks()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    Log.e("rrr", "getAllBooks data: " + it)
//                },
//                {
//                    Log.e("rrr", "getAllBooks error:" + it.message)
//                },
//            )


    }

    fun insertToUser() {
        val values = ContentValues()
        values.put(PrivderConstants.KEY_USER, Gson().toJson(UserEntity(userName = "riad21")))
        contentResolver.insert(PrivderConstants.URI_USERS, values)
    }

    fun insertToBook() {
        val values = ContentValues()
        values.put(
            PrivderConstants.KEY_BOOK,
            Gson().toJson(BookEntity(name = "game of thrones 2", type = "fantasy"))
        )
        contentResolver.insert(PrivderConstants.URI_BOOKS, values)
    }

    fun getAllUsersFromContentProvider(){
        var uri = PrivderConstants.URI_USERS
        Log.e(
            "rrr",
            "getAllUsersFromContentProvider uri:  " + uri
        )
        var cursor = contentResolver?.query(uri, null, null, null, null)

        while (cursor?.moveToNext() == true){
            Log.e("rrr", "getAllUsersFromContentProvider cursor: " + Gson().toJson(cursor.getString(1)))
        }

        cursor?.close()
    }

    fun getUserFromContentProvider(id: Int){
        var uri = Uri.withAppendedPath(PrivderConstants.URI_USERS, id.toString())
        Log.e(
            "rrr",
            "getUserFromContentProvider uri:  " + uri
        )
        var cursor = contentResolver?.query(uri, null, null, null, null)

        while (cursor?.moveToNext() == true){
            Log.e("rrr", "getUserFromContentProvider cursor: " + Gson().toJson(cursor.getString(1)))
        }

        cursor?.close()
    }


    fun getAllData() {
        getAllUsers()
        getAllBooks()
    }


    fun getAllUsers() {
        var disposableUser2 = UserRepository.getAllUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.e("rrr", "getAllUsers data: " + it)
                },
                {
                    Log.e("rrr", "getUser error:" + it.message)
                },
            )
    }

    fun getAllBooks() {
        var disposableUser2 = BookRepository.getAllBooks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.e("rrr", "getAllBooks data: " + it)
                },
                {
                    Log.e("rrr", "getAllBooks error:" + it.message)
                },
            )
    }
}
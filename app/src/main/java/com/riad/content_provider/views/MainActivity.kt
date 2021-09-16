package com.riad.content_provider.views

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.riad.content_provider.R
import com.riad.content_provider.data.db.BookRepository
import com.riad.content_provider.data.db.UserRepository
import com.riad.content_provider.data.db.entity.BookEntity
import com.riad.content_provider.data.db.entity.UserEntity
import com.riad.content_provider.data.provider.PrivderConstants
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        button_user.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
        }

        button_book.setOnClickListener {
            val intent = Intent(this, BookActivity::class.java)
            startActivity(intent)
        }


        getAllData()

//        var disposableUser = Single.fromCallable {
//            // method that run in background
//            getAllUsersFromContentProvider()
////            getUserFromContentProvider(3)
//
//        }
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//
//                },
//                {
//
//                }
//            )


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
                    text_view_user.text = it.toString()
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
                    text_view_books.text = it.toString()
                },
                {
                    Log.e("rrr", "getAllBooks error:" + it.message)
                },
            )
    }
}
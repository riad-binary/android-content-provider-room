package com.riad.content_provider.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.riad.content_provider.R
import com.riad.content_provider.data.db.BookRepository
import com.riad.content_provider.data.db.UserRepository
import com.riad.content_provider.data.db.entity.BookEntity
import com.riad.content_provider.data.db.entity.UserEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var userRepository = UserRepository()
        var bookRepository = BookRepository()


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


        var disposableUser2 = userRepository.getAllUsers()
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

        var disposableBook2 = bookRepository.getAllBooks()
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
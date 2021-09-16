package com.riad.content_provider.data.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.media.UnsupportedSchemeException
import android.net.Uri
import android.util.Log
import com.google.gson.Gson
import com.riad.content_provider.data.db.BookRepository
import com.riad.content_provider.data.db.UserRepository
import com.riad.content_provider.data.db.entity.BookEntity
import com.riad.content_provider.data.db.entity.UserEntity
import com.riad.content_provider.data.provider.PrivderConstants.AUTHORITY
import com.riad.content_provider.data.provider.PrivderConstants.BOOKS
import com.riad.content_provider.data.provider.PrivderConstants.BOOKS_BY_ID
import com.riad.content_provider.data.provider.PrivderConstants.BOOK_PATH
import com.riad.content_provider.data.provider.PrivderConstants.KEY_BOOK
import com.riad.content_provider.data.provider.PrivderConstants.KEY_USER
import com.riad.content_provider.data.provider.PrivderConstants.USERS
import com.riad.content_provider.data.provider.PrivderConstants.USERS_BY_ID
import com.riad.content_provider.data.provider.PrivderConstants.USER_PATH
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DataProvider : ContentProvider() {

    private lateinit var mUriMatcher: UriMatcher

    override fun onCreate(): Boolean {
        mUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        mUriMatcher.addURI(AUTHORITY, USER_PATH, USERS)
        mUriMatcher.addURI(AUTHORITY, "$USER_PATH/#", USERS_BY_ID)
        mUriMatcher.addURI(AUTHORITY, BOOK_PATH, BOOKS)
        mUriMatcher.addURI(AUTHORITY, "$BOOK_PATH/#", BOOKS_BY_ID)

        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        Log.e(
            "rrr",
            "DataProvider inser uri:  " + uri
        )

        return when{
            mUriMatcher.match(uri) == USERS -> {
                val cursor = UserRepository.getAllUsersCursor()
                cursor.setNotificationUri(context?.contentResolver, uri)
                cursor
            }
            mUriMatcher.match(uri) == USERS_BY_ID -> {
                val cursor = UserRepository.getUserCursor(uri.lastPathSegment!!.toInt())
                cursor.setNotificationUri(context?.contentResolver, uri)
                cursor
            }
            else -> {
                throw UnsupportedSchemeException("Uri UnsupportedSchemeException")
            }
        }
    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Log.e(
            "rrr",
            "DataProvider inser uri:  " + uri
        )
        if (mUriMatcher.match(uri) == USERS) {

            val user = Gson().fromJson(
                values?.get(KEY_USER).toString(),
                UserEntity::class.java
            )
            Log.e(
                "rrr",
                "DataProvider insertUser user:  " + user
            )

            var disposable = UserRepository.insertUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Log.e("rrr", "DataProvider insertUser complete:  ")
                        context?.contentResolver?.notifyChange(uri, null)
                    },
                    {
                        Log.e("rrr", "DataProvider insertUser error:" + it.message)
                    }
                )



            return null
        } else if (mUriMatcher.match(uri) == BOOKS) {

            val book = Gson().fromJson(
                values?.get(KEY_BOOK).toString(),
                BookEntity::class.java
            )
            Log.e(
                "rrr",
                "DataProvider insertBook book:  " + book
            )

            var disposable = BookRepository.insertBook(book)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Log.e("rrr", "DataProvider insertBook complete:  ")
                        context?.contentResolver?.notifyChange(uri, null)
                    },
                    {
                        Log.e("rrr", "DataProvider insertBook error:" + it.message)
                    }
                )



            return null
        } else {
            throw UnsupportedSchemeException("Uri invalid")
        }
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

}
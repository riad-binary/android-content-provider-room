package com.riad.content_provider.views

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.riad.content_provider.R
import com.riad.content_provider.data.db.entity.UserEntity
import com.riad.content_provider.data.provider.PrivderConstants
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)


        button_get_user.setOnClickListener {
            if (!edit_user_id.text.toString().isNullOrBlank()) {
                var disposable = getUserFromContentProvider(edit_user_id.text.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            Log.e("rrr", "getUserFromContentProvider data: " + it)
                            text_view_user_by_id.text = it.toString()
                        },
                        {
                            Log.e("rrr", "getUserFromContentProvider error:" + it.message)
                        },
                    )
            }

        }

        button_update_user.setOnClickListener {
            if (!edit_update_user_id.text.toString().isNullOrBlank()) {
                updateUser(edit_update_user_id.text.toString(), edit_username.text.toString())
            }else if(!edit_old_user_name.text.toString().isNullOrBlank()) {
                updateUserByName(edit_old_user_name.text.toString(), edit_username.text.toString())
            }
        }

        button_insert_user.setOnClickListener {
            insertToUser(edit_username.text.toString())
        }

        button_get_all_user.setOnClickListener {
            var disposable = getAllUsersFromContentProvider()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Log.e("rrr", "getAllUsersFromContentProvider data: " + it)
                        text_view_user.text = it.toString()
                    },
                    {
                        Log.e("rrr", "getAllUsersFromContentProvider error:" + it.message)
                    },
                )
        }


    }


    fun insertToUser(name: String) {
        if (name.isNullOrEmpty()) return
        val values = ContentValues()
        values.put(PrivderConstants.KEY_USER, Gson().toJson(UserEntity(userName = name)))
        contentResolver.insert(PrivderConstants.URI_USERS, values)
    }

    fun getAllUsersFromContentProvider(): Single<List<UserEntity>> {
        val userListSingle: Single<List<UserEntity>> = Single.create {
            var uri = PrivderConstants.URI_USERS
            Log.e(
                "rrr",
                "getAllUsersFromContentProvider uri:  " + uri
            )

            var userList: MutableList<UserEntity> = arrayListOf()

            var cursor = contentResolver?.query(uri, null, null, null, null)

            while (cursor?.moveToNext() == true) {
                userList.add(UserEntity(cursor.getInt(0), cursor.getString(1)))
            }
            cursor?.close()

            it.onSuccess(userList)
        }

        return userListSingle

    }


    fun getUserFromContentProvider(id: String): Single<UserEntity?> {
        val userSingle: Single<UserEntity?> = Single.create {
            var uri = Uri.withAppendedPath(PrivderConstants.URI_USERS, id)
            Log.e(
                "rrr",
                "getUserFromContentProvider uri:  " + uri
            )
            var cursor = contentResolver?.query(uri, null, null, null, null)

            var user: UserEntity? = null

            while (cursor?.moveToNext() == true) {
                user = UserEntity(cursor.getInt(0), cursor.getString(1))
            }
            cursor?.close()

            if (user != null) {
                it.onSuccess(user)
            } else {
                it.onError(Throwable())
            }
        }

        return userSingle
    }

    fun updateUser(id: String, name: String) {
        if (id.isNullOrEmpty()) return
        val uri = Uri.withAppendedPath(PrivderConstants.URI_USERS, id)
        val values = ContentValues()
        values.put(PrivderConstants.KEY_USER, Gson().toJson(UserEntity(id.toInt(), userName = name)))
        contentResolver.update(uri, values, null, null)
    }

    fun updateUserByName(oldName: String, name: String) {
        if (oldName.isNullOrEmpty()) return
        val uri = Uri.withAppendedPath(PrivderConstants.URI_USERS, oldName)
        val values = ContentValues()
        values.put(PrivderConstants.KEY_USER, Gson().toJson(UserEntity(1, userName = name)))
        contentResolver.update(uri, values, null, null)
    }


}
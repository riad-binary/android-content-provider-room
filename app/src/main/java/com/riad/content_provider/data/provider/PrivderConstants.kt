package com.riad.content_provider.data.provider

import android.net.Uri

object PrivderConstants {
    const val AUTHORITY = "com.riad.content_provider.provider"
    val BASE_URI = Uri.parse("content://$AUTHORITY")

    const val USER_PATH = "users"
    const val BOOK_PATH = "books"

    val URI_USERS = Uri.withAppendedPath(BASE_URI, USER_PATH)
    val URI_BOOKS= Uri.withAppendedPath(BASE_URI, BOOK_PATH)



    const val USERS = 1
    const val USERS_BY_ID = 2
    const val BOOKS = 3
    const val BOOKS_BY_ID = 4

    const val KEY_USER = "user"
    const val KEY_BOOK = "book"
}
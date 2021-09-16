package com.riad.content_provider.data.db

import android.database.Cursor
import com.riad.content_provider.App
import com.riad.content_provider.data.db.dao.BookDao
import com.riad.content_provider.data.db.entity.BookEntity
import io.reactivex.Completable
import io.reactivex.Flowable

object BookRepository {

    var db: BookDao = AppDatabase.getInstance(App.instance)?.bookDao()!!

    //Fetch All the book
    fun getAllBooks(): Flowable<List<BookEntity>> {
        return db.gelAllBooks()
    }

    fun getAllBooksCursor(): Cursor {
        return db.gelAllBooksCursor()
    }

    // Insert new book
    fun insertBook(book: BookEntity): Completable {
        return db.insertBook(book)
    }

    fun getBook(id: Int): Flowable<BookEntity> {
        return db.getBook(id)
    }

    fun getBookCursor(id: Int): Cursor {
        return db.getBookCursor(id)
    }

    fun deleteBookById(id: Int): Completable {
        return db.deleteBookById(id)
    }

    // update book
    fun updateBook(book: BookEntity): Completable {
        return db.updateBook(book)
    }

    // Delete book
    fun deleteBook(book: BookEntity): Completable {
        return db.deleteBook(book)
    }
}
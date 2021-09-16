package com.riad.content_provider.views

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.riad.content_provider.R
import com.riad.content_provider.data.db.entity.BookEntity
import com.riad.content_provider.data.provider.PrivderConstants
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_book.*

class BookActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)

        button_get_book.setOnClickListener {
            if (!edit_book_id.text.toString().isNullOrBlank()){
                var disposable = getBookFromContentProvider(edit_book_id.text.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            Log.e("rrr", "getBookFromContentProvider data: " + it)
                            text_view_book_by_id.text = it.toString()
                        },
                        {
                            Log.e("rrr", "getBookFromContentProvider error:" + it.message)
                        },
                    )
            }

        }

        button_insert_book.setOnClickListener {
            insertToBook(edit_book_name.text.toString(), edit_book_type.text.toString())
        }

        button_get_all_books.setOnClickListener {
            var disposable = getAllBookFromContentProvider()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Log.e("rrr", "getAllBookFromContentProvider data: " + it)
                        text_view_books.text = it.toString()
                    },
                    {
                        Log.e("rrr", "getAllBookFromContentProvider error:" + it.message)
                    },
                )
        }

    }

    fun insertToBook(name: String, type: String) {
        if(name.isNullOrEmpty() && type.isNullOrEmpty()) return
        val values = ContentValues()
        values.put(PrivderConstants.KEY_BOOK, Gson().toJson(BookEntity(name = name, type = type)))
        contentResolver.insert(PrivderConstants.URI_BOOKS, values)
    }

    fun getAllBookFromContentProvider(): Single<List<BookEntity>> {
        val bookListSingle: Single<List<BookEntity>> = Single.create{
            var uri = PrivderConstants.URI_BOOKS
            Log.e(
                "rrr",
                "getAllUsersFromContentProvider uri:  " + uri
            )

            var  bookList: MutableList<BookEntity> = arrayListOf()

            var cursor = contentResolver?.query(uri, null, null, null, null)

            while (cursor?.moveToNext() == true){
                bookList.add(BookEntity(cursor.getInt(0), cursor.getString(1), cursor.getString(2)))
            }
            cursor?.close()

            it.onSuccess(bookList)
        }

        return bookListSingle

    }


    fun getBookFromContentProvider(id: String): Single<BookEntity?> {
        val bookSingle: Single<BookEntity?> = Single.create{
            var uri = Uri.withAppendedPath(PrivderConstants.URI_BOOKS, id)
            Log.e(
                "rrr",
                "getUserFromContentProvider uri:  " + uri
            )
            var cursor = contentResolver?.query(uri, null, null, null, null)

            var book: BookEntity? = null

            while (cursor?.moveToNext() == true){
                book = BookEntity(cursor.getInt(0), cursor.getString(1), cursor.getString(2))
            }
            cursor?.close()

            if (book != null) {
                it.onSuccess(book)
            } else{
                it.onError(Throwable())
            }
        }

        return bookSingle
    }


}
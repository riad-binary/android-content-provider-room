package com.riad.content_provider.data.db.dao

import androidx.room.*
import com.riad.content_provider.data.db.entity.BookEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface BookDao {

    @Insert
    fun insertBook(book: BookEntity): Completable

    @Query("Select * from history")
    fun gelAllBooks(): Flowable<List<BookEntity>>

    @Query("SELECT * FROM history WHERE id = :id")
    fun getBook(id: Int): Flowable<BookEntity>

    @Query("DELETE FROM history WHERE id = :id")
    fun deleteBookById(id: Int): Completable

    @Update
    fun updateBook(book: BookEntity): Completable

    @Delete
    fun deleteBook(book: BookEntity): Completable

}
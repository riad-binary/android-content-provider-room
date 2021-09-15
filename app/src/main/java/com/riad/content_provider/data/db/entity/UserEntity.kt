package com.riad.content_provider.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var userId: Int? = null,

    @ColumnInfo(name = "username")
    val userName: String
    )

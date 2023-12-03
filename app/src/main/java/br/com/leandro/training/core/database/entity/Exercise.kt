package br.com.leandro.training.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "exercise")
data class Exercise(
    @PrimaryKey(autoGenerate = true) val name: Long = 0,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "comments") val comments: String,
    var selected: Boolean? = false
) : Serializable
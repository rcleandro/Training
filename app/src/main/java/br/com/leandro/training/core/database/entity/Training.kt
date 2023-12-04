package br.com.leandro.training.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import br.com.leandro.training.core.database.ExercisesConverter
import java.io.Serializable

@Entity(tableName = "training")
data class Training(
    @PrimaryKey(autoGenerate = true) val name: Long = 0,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "date") val date: Long,
    @TypeConverters(ExercisesConverter::class)
    @ColumnInfo(name = "exercises") val exercises: List<Exercise>
) : Serializable
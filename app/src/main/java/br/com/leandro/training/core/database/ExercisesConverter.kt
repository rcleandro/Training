package br.com.leandro.training.core.database

import androidx.room.TypeConverter
import br.com.leandro.training.core.database.entity.Exercise
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ExercisesConverter {

    @TypeConverter
    fun fromExercises(value: List<Exercise>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Exercise>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toExercises(value: String): List<Exercise> {
        val gson = Gson()
        val type = object : TypeToken<List<Exercise>>() {}.type
        return gson.fromJson(value, type)
    }
}
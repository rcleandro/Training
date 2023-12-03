package br.com.leandro.training.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.leandro.training.core.database.dao.ExerciseDao
import br.com.leandro.training.core.database.dao.TrainingDao
import br.com.leandro.training.core.database.entity.Exercise
import br.com.leandro.training.core.database.entity.Training

@Database(entities = [Training::class, Exercise::class], version = 1, exportSchema = false)
@TypeConverters(ExercisesConverter::class)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun trainingDao(): TrainingDao

    abstract fun exerciseDao(): ExerciseDao

    companion object {

        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                synchronized(AppDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, DATABASE_NAME
                    )
                        .build()
                }
            }
            return instance!!
        }

        private const val DATABASE_NAME = "app-database.db"
    }
}
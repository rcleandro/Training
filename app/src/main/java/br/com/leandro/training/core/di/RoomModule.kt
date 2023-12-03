package br.com.leandro.training.core.di

import android.app.Application
import br.com.leandro.training.core.database.AppDatabase
import br.com.leandro.training.core.database.dao.ExerciseDao
import br.com.leandro.training.core.database.dao.TrainingDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object RoomModule {

    @Singleton
    @Provides
    fun providesRoomDatabase(application: Application): AppDatabase {
        return AppDatabase.getInstance(application)
    }

    @Singleton
    @Provides
    fun providesTrainingDao(database: AppDatabase): TrainingDao {
        return database.trainingDao()
    }

    @Singleton
    @Provides
    fun providesExerciseDao(database: AppDatabase): ExerciseDao {
        return database.exerciseDao()
    }
}
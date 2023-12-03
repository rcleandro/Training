package br.com.leandro.training.core.di

import br.com.leandro.training.core.repository.ExerciseRepositoryImpl
import br.com.leandro.training.core.repository.ExercisesRepository
import br.com.leandro.training.core.repository.TrainingRepository
import br.com.leandro.training.core.repository.TrainingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun providesTrainingRepository(impl: TrainingRepositoryImpl): TrainingRepository

    @Singleton
    @Binds
    abstract fun providesExercisesRepository(impl: ExerciseRepositoryImpl): ExercisesRepository
}

package br.com.leandro.training.core.di

import br.com.leandro.training.domain.GetExerciseUseCase
import br.com.leandro.training.domain.GetExerciseUseCaseImpl
import br.com.leandro.training.domain.GetTrainingUseCase
import br.com.leandro.training.domain.GetTrainingUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class CollectionsModule {

  @Singleton
  @Binds
  abstract fun providesTrainingUseCase(
    impl: GetTrainingUseCaseImpl
  ): GetTrainingUseCase

  @Singleton
  @Binds
  abstract fun providesExerciseUseCase(
    impl: GetExerciseUseCaseImpl
  ): GetExerciseUseCase
}

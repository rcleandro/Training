package br.com.leandro.training.core.repository

import br.com.leandro.training.core.database.entity.Exercise

interface ExercisesRepository {

    suspend fun fetchAll(): List<Exercise>

    suspend fun fetchExerciseByName(name: Long): Exercise

    suspend fun add(exercise: Exercise)

    suspend fun update(exercise: Exercise)

    suspend fun delete(name: Long)
}
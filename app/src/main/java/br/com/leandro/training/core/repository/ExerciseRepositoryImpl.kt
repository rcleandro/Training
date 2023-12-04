package br.com.leandro.training.core.repository

import android.util.Log
import br.com.leandro.training.core.database.dao.ExerciseDao
import br.com.leandro.training.core.database.entity.Exercise
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(
    private val dao: ExerciseDao
) : ExercisesRepository {

    override suspend fun fetchAll(): List<Exercise> {
        Log.d(TAG, "Fetching all exercises")
        return dao.fetchAll()
            .map { exercise ->
                Exercise(
                    name = exercise.name,
                    image = exercise.image,
                    comments = exercise.comments
                )
            }
    }

    override suspend fun fetchExerciseByName(name: Long): Exercise {
        Log.d(TAG, "Fetching exercise by name $name")
        val exercise = dao.fetchExerciseByName(name)
        return Exercise(
            name = exercise.name,
            image = exercise.image,
            comments = exercise.comments
        )
    }

    override suspend fun add(exercise: Exercise) {
        Log.d(TAG, "Adding new exercise ${exercise.name}")
        dao.add(exercise)
    }

    override suspend fun update(exercise: Exercise) {
        Log.d(TAG, "Editing exercise ${exercise.name}")
        dao.update(exercise)
    }

    override suspend fun delete(name: Long) {
        Log.d(TAG, "Deleting exercise with name $name")
        dao.delete(name)
    }

    companion object {
        private const val TAG = "ExerciseRepository"
    }
}
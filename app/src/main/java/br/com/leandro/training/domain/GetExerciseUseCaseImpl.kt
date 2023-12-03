package br.com.leandro.training.domain

import android.util.Log
import br.com.leandro.training.core.database.entity.Exercise
import br.com.leandro.training.core.repository.ExercisesRepository
import javax.inject.Inject

class GetExerciseUseCaseImpl @Inject constructor(
    private val exerciseRepository: ExercisesRepository,
) : GetExerciseUseCase {

    override suspend fun invoke(): List<Exercise> {
        Log.d(TAG, "Fetching all exercise")

        return exerciseRepository
            .fetchAll()
            .map { exercise ->
                Exercise(
                    name = exercise.name,
                    image = exercise.image,
                    comments = exercise.comments
                )
            }
    }

    companion object {
        private const val TAG = "GetExercise"
    }
}
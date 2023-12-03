package br.com.leandro.training.domain

import android.util.Log
import br.com.leandro.training.core.database.entity.Training
import br.com.leandro.training.core.repository.TrainingRepository
import javax.inject.Inject

class GetTrainingUseCaseImpl @Inject constructor(
    private val trainingRepository: TrainingRepository,
) : GetTrainingUseCase {

    override suspend fun invoke(): List<Training> {
        Log.d(TAG, "Fetching all training")

        return trainingRepository
            .fetchAll()
            .map { training ->
                Training(
                    name = training.name,
                    description = training.description,
                    date = training.date,
                    exercises = training.exercises
                )
            }
    }

    companion object {
        private const val TAG = "GetTraining"
    }
}
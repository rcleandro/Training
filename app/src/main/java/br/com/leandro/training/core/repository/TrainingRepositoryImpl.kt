package br.com.leandro.training.core.repository

import android.util.Log
import br.com.leandro.training.core.database.dao.TrainingDao
import br.com.leandro.training.core.database.entity.Training
import javax.inject.Inject

class TrainingRepositoryImpl @Inject constructor(
    private val dao: TrainingDao
) : TrainingRepository {

    override suspend fun fetchAll(): List<Training> {
        Log.d(TAG, "Fetching all trainings")
        return dao.fetchAll()
            .map { training ->
                Training(
                    name = training.name,
                    description = training.description,
                    date = training.date,
                    exercises = training.exercises
                )
            }
    }

    override suspend fun fetchTrainingByName(name: Long): Training {
        Log.d(TAG, "Fetching training by name $name")
        val training = dao.fetchTrainingByName(name)
        return Training(
            name = training.name,
            description = training.description,
            date = training.date,
            exercises = training.exercises
        )
    }

    override suspend fun add(training: Training) {
        Log.d(TAG, "Adding new training ${training.name}")
        dao.add(training)
    }

    override suspend fun update(training: Training) {
        Log.d(TAG, "Editing training ${training.name}")
        dao.update(training)
    }

    override suspend fun delete(name: Long) {
        Log.d(TAG, "Deleting training with name $name")
        dao.delete(name)
    }

    companion object {
        private const val TAG = "TrainingRepository"
    }
}
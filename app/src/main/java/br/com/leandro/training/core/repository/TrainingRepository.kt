package br.com.leandro.training.core.repository

import br.com.leandro.training.core.database.entity.Training

interface TrainingRepository {

    suspend fun fetchAll(): List<Training>

    suspend fun fetchTrainingByName(name: Long): Training

    suspend fun add(training: Training)

    suspend fun delete(name: Long)
}
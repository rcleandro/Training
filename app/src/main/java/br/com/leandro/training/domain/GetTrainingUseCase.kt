package br.com.leandro.training.domain

import br.com.leandro.training.core.database.entity.Training

interface GetTrainingUseCase {

    suspend operator fun invoke(): List<Training>
}
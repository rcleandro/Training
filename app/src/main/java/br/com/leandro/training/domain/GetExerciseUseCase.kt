package br.com.leandro.training.domain

import br.com.leandro.training.core.database.entity.Exercise

interface GetExerciseUseCase {

    suspend operator fun invoke(): List<Exercise>
}
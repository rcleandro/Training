package br.com.leandro.training.domain

import br.com.leandro.training.core.database.entity.Profile

interface GetProfileUseCase {

    suspend operator fun invoke(): Profile
}
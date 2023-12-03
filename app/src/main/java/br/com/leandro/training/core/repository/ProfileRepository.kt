package br.com.leandro.training.core.repository

import br.com.leandro.training.core.database.entity.Profile

interface ProfileRepository {
    fun fetchProfile(): Profile
}
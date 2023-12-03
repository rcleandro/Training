package br.com.leandro.training.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.leandro.training.core.database.entity.Exercise

@Dao
interface ExerciseDao {

    @Query("SELECT * FROM exercise")
    suspend fun fetchAll(): List<Exercise>

    @Query(
        """
    SELECT * FROM exercise
    WHERE name LIKE :name
  """
    )
    suspend fun fetchExerciseByName(name: Long): Exercise

    @Insert
    suspend fun add(exercise: Exercise)

    @Query(
        """
    DELETE FROM exercise WHERE name = :name
    """
    )
    suspend fun delete(name: Long)
}
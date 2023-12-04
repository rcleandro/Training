package br.com.leandro.training.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.leandro.training.core.database.entity.Training

@Dao
interface TrainingDao {

    @Query("SELECT * FROM training")
    suspend fun fetchAll(): List<Training>

    @Query(
        """
    SELECT * FROM training
    WHERE name LIKE :name
  """
    )
    suspend fun fetchTrainingByName(name: Long): Training

    @Insert
    suspend fun add(training: Training)

    @Update
    suspend fun update(training: Training)

    @Query(
        """
    DELETE FROM training WHERE name = :name
    """
    )
    suspend fun delete(name: Long)
}
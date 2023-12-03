package br.com.leandro.training.ui.exercises

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.leandro.training.core.database.entity.Exercise
import br.com.leandro.training.core.repository.ExercisesRepository
import br.com.leandro.training.domain.GetExerciseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ExerciseListViewModel @Inject constructor(
    private val getExerciseUseCase: GetExerciseUseCase,
    private val exercisesRepository: ExercisesRepository
) : ViewModel() {

    /**
     * Mutable Live Data that initialize with the current list of saved Exercise.
     */
    private val uiState: MutableLiveData<UiState> by lazy {
        MutableLiveData<UiState>(UiState(exerciseList = listOf()))
    }

    /**
     * Refresh UI State whenever View Resumes.
     */
    fun onResume() {
        viewModelScope.launch {
            refreshExerciseList()
        }
    }

    /**
     * Expose the uiState as LiveData to UI.
     */
    fun stateOnceAndStream(): LiveData<UiState> = uiState

    private suspend fun refreshExerciseList() {
        uiState.postValue(UiState(getExerciseUseCase()))
    }

    /**
     * UI State containing every data needed to show Exercise.
     */
    data class UiState(val exerciseList: List<Exercise>)

    /**
     * Delete exercise.
     *
     * @param name: The name of the exercise you want to delete
     */
    fun deleteExercise(name: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                exercisesRepository.delete(name)
            }

            withContext(Dispatchers.Main) {
                refreshExerciseList()
            }
        }
    }
}
package br.com.leandro.training.ui.training

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.leandro.training.core.database.entity.Exercise
import br.com.leandro.training.core.database.entity.Training
import br.com.leandro.training.core.repository.TrainingRepository
import br.com.leandro.training.domain.GetExerciseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddTrainingViewModel @Inject constructor(
    private val getExerciseUseCase: GetExerciseUseCase,
    private val trainingRepository: TrainingRepository
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

    private val onSaved = MutableLiveData<Boolean>().apply { value = false }
    fun onSaved(): LiveData<Boolean> = onSaved

    /**
     * Add new training.
     *
     * @param description: The description you want to give to this exercise
     * @param exercises: The exercises you want to give to this exercise
     */
    fun addTraining(description: String, exercises: List<Exercise>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                trainingRepository.add(
                    Training(
                        description = description,
                        date = Date().time,
                        exercises = exercises
                    )
                )
            }

            withContext(Dispatchers.Main) {
                onSaved.postValue(true)
            }
        }
    }
}
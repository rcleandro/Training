package br.com.leandro.training.ui.training

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.leandro.training.core.database.entity.Training
import br.com.leandro.training.core.repository.TrainingRepository
import br.com.leandro.training.domain.GetTrainingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TrainingListViewModel @Inject constructor(
    private val getTrainingUseCase: GetTrainingUseCase,
    private val trainingRepository: TrainingRepository
) : ViewModel() {

    /**
     * Mutable Live Data that initialize with the current list of saved Training.
     */
    private val uiState: MutableLiveData<UiState> by lazy {
        MutableLiveData<UiState>(UiState(trainingList = listOf()))
    }

    /**
     * Refresh UI State whenever View Resumes.
     */
    fun onResume() {
        viewModelScope.launch {
            refreshTrainingList()
        }
    }

    /**
     * Expose the uiState as LiveData to UI.
     */
    fun stateOnceAndStream(): LiveData<UiState> = uiState

    private suspend fun refreshTrainingList() {
        uiState.postValue(UiState(getTrainingUseCase()))
    }

    /**
     * UI State containing every data needed to show Training.
     */
    data class UiState(val trainingList: List<Training>)

    /**
     * Delete training.
     *
     * @param name: The name of the training you want to delete
     */
    fun deleteTraining(name: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                trainingRepository.delete(name)
            }

            withContext(Dispatchers.Main) {
                refreshTrainingList()
            }
        }
    }
}
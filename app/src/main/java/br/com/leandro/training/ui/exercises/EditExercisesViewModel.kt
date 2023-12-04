package br.com.leandro.training.ui.exercises

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.leandro.training.core.database.entity.Exercise
import br.com.leandro.training.core.repository.ExercisesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditExercisesViewModel @Inject constructor(
    private val exercisesRepository: ExercisesRepository
) : ViewModel() {

    private val onSaved = MutableLiveData<Boolean>().apply { value = false }
    fun onSaved(): LiveData<Boolean> = onSaved

    /**
     * Edit exercise.
     *
     * @param comment: The comments you want to update to this exercise
     * @param image: The image you want to update to this exercise
     */
    fun editExercise(name: Long, comment: String, image: String?) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                exercisesRepository.update(
                    Exercise(
                        name = name,
                        comments = comment,
                        image = image
                    )
                )
            }

            withContext(Dispatchers.Main) {
                onSaved.postValue(true)
            }
        }
    }
}
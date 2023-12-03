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
class AddExerciseViewModel @Inject constructor(
    private val exercisesRepository: ExercisesRepository
) : ViewModel() {

    private val onSaved = MutableLiveData<Boolean>().apply { value = false }
    fun onSaved(): LiveData<Boolean> = onSaved

    /**
     * Add new exercise.
     *
     * @param comment: The comments you want to give to this exercise
     * @param image: The image you want to give to this exercise
     */
    fun addExercise(comment: String, image: String?) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                exercisesRepository.add(
                    Exercise(
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